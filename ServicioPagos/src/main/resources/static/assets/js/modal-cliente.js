// -------------------------------
// MODAL 1: Abrir modal de servicio
// -------------------------------

let pagoIdActual = null;

function abrirModalSuministro(nombreServicio, nombreEmpresa, idServicio) {
    document.getElementById('servicioIdHidden').value = idServicio;
    document.getElementById('servicioHidden').value = nombreServicio;
    document.getElementById('empresaHidden').value = nombreEmpresa;

    document.getElementById('tituloServicio').innerText = `${nombreServicio} - Código de Suministro`;
    $('#sedapalModal').modal('show');
}

// --------------------------------
// MODAL 2: Continuar al modal cliente
// --------------------------------
function continuarCliente() {
    document.getElementById('empresaTexto').innerText = document.getElementById('empresaHidden').value;
    document.getElementById('servicioTexto').innerText = document.getElementById('servicioHidden').value;
    document.getElementById('codigoTexto').innerText = document.getElementById('codigoSuministro').value;

    $('#sedapalModal').modal('hide');
    $('#modalCliente').modal('show');
}

// --------------------------------
// MODAL 3: Abrir modal de confirmación
// --------------------------------
function abrirConfirmacion() {
    const empresa = document.getElementById('empresaTexto').innerText;
    const servicio = document.getElementById('servicioTexto').innerText;
    const codigo = document.getElementById('codigoTexto').innerText;
    const nombreCliente = document.getElementById('nombre').value;
    const telefono = document.getElementById('telefono').value;
    const metodoPago = document.getElementById('metodoPago').selectedOptions[0].text;
    const monto = document.getElementById('monto').value;
    const descripcion = document.getElementById('descripcion').value;

    const contenido = `
        <div class="row mb-2">
            <div class="col-md-6"><strong>Empresa:</strong> ${empresa}</div>
            <div class="col-md-6"><strong>Servicio:</strong> ${servicio}</div>
        </div>
        <div class="row mb-2">
            <div class="col-md-6"><strong>Código de Suministro:</strong> ${codigo}</div>
            <div class="col-md-6"><strong>Cliente:</strong> ${nombreCliente}</div>
        </div>
        <div class="row mb-2">
            <div class="col-md-6"><strong>Teléfono:</strong> ${telefono}</div>
            <div class="col-md-6"><strong>Método de Pago:</strong> ${metodoPago}</div>
        </div>
        <div class="row mb-2">
            <div class="col-md-6"><strong>Monto:</strong> ${monto}</div>
            <div class="col-md-6"><strong>Servicios / Publicidad:</strong> ${descripcion}</div>
        </div>
    `;

    document.getElementById('contenidoConfirmacion').innerHTML = contenido;

    $('#modalCliente').modal('hide');
    $('#modalConfirmacion').modal('show');
}

// --------------------------------
// Volver al modal cliente
// --------------------------------
function volverAlCliente() {
    $('#modalConfirmacion').modal('hide');
    $('#modalCliente').modal('show');
}

// --------------------------------
// Guardar cuenta en backend
// --------------------------------
function guardarCuenta() {
    const idMetodoPago = document.getElementById('metodoPago').value;
    const descripcion = document.getElementById('descripcion').value.trim();
    document.getElementById('metodoPagoIdHidden').value = idMetodoPago;
    const empresaServicio = document.getElementById('empresaTexto').innerText;

    const cuentaData = new URLSearchParams();
    cuentaData.append("codigoSuministro", document.getElementById('codigoSuministro').value);
    cuentaData.append("nombreCliente", document.getElementById('nombre').value);
    cuentaData.append("telefono", document.getElementById('telefono').value);
    cuentaData.append("monto", document.getElementById('monto').value);
    cuentaData.append("serviciosOfrecidos", document.getElementById('descripcion').value);
    cuentaData.append("publicidad", descripcion);
    cuentaData.append("idServicio", document.getElementById('servicioIdHidden').value);
    cuentaData.append("idMetodoPago", idMetodoPago);

    fetch("/cuenta/registrar", {
        method: "POST",
        body: cuentaData
    })
    .then(res => res.json())
    .then(cuentaResp => {
        console.log("Cuenta guardada:", cuentaResp);

        const pagoData = {
            idUsuarios: 1,
            idServicios: document.getElementById('servicioIdHidden').value,
            idMetodo: idMetodoPago,
            idCuenta: cuentaResp.idCuentaSuministro
        };

        return fetch("/pagos/generar", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(pagoData)
        });
    })
    .then(res => res.json())
    .then(pagoResp => {
        console.log("Pago guardado:", pagoResp);

        // ---------------------------
        // ASIGNAR EL ID DEL PAGO PARA PDF
        // ---------------------------
            if (!pagoResp.idPagos) {
                    alert("Error: el pago no se generó correctamente.");
                    return;
                }
                     pagoIdActual = pagoResp.idPagos;
                        document.getElementById('pagoIdHidden').value = pagoIdActual;


        const contenido = `
        <div class="card p-4 shadow-sm border">
            <div class="row mb-3">
                <div class="col-md-12 text-center"><strong>${pagoResp.nombreEmpresa}</strong></div>
            </div>
            <div class="row mb-3">
                <div class="col-md-12 text-center">${pagoResp.direccion}</div>
            </div>
            <div class="row mb-3">
                <div class="col-md-4"><strong>Entidad:</strong> ${empresaServicio}</div>
                <div class="col-md-4"><strong>Servicio:</strong> ${pagoResp.servicio}</div>
                <div class="col-md-4"><strong>Código Suministro:</strong> ${pagoResp.cliente.codigoSuministro}</div>
            </div>
            <div class="row mb-3">
                <div class="col-md-4"><strong>Monto a Pagar:</strong> S/. ${pagoResp.monto}</div>
                <div class="col-md-4"><strong>Método Pago:</strong> ${pagoResp.metodoPago}</div>
                <div class="col-md-4"><strong>Fecha:</strong> ${new Date(pagoResp.fechaPago).toLocaleString()}</div>
            </div>
            <div class="row mb-3">
                <div class="col-md-6"><strong>Código Operación:</strong> ${pagoResp.codigoOperacion}</div>
                <div class="col-md-6"><strong>Número Comprobante:</strong> ${pagoResp.numeroComprobante}</div>
            </div>
            <div class="row mb-3">
                <div class="col-md-6"><strong>Publicidad:</strong> ${pagoResp.publicidad}</div>
                <div class="col-md-6"><strong>Servicios Ofrecidos:</strong> ${pagoResp.serviciosOfrecidos}</div>
            </div>
        </div>
        `;

        document.getElementById('contenidoComprobante').innerHTML = contenido;
        $('#modalConfirmacion').modal('hide');
        $('#modalComprobante').modal('show');
    })
    .catch(err => {
        console.error(err);
        alert("Error al registrar el pago");
    });
}

// ---------------------------
// DESCARGAR PDF
// ---------------------------
document.getElementById('btnDescargarPdf').addEventListener('click', () => {
    const id = document.getElementById('pagoIdHidden').value;
    if (!id) {
        alert("No hay un pago seleccionado para descargar.");
        return;
    }

    // Cerrar modal
    $('#modalComprobante').modal('hide');

    // Forzar descarga
    const link = document.createElement('a');
    link.href = '/pagos/pdf/' + id;
    link.download = 'pago_' + id + '.pdf';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

    // Redirigir después de descarga
    setTimeout(() => {
        window.location.href = '/pagos/ver';
    }, 500);
});
