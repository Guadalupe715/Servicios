// -------------------------------
// MODAL 1: Abrir modal de servicio
// -------------------------------
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
    const codigoSuministro = document.getElementById('codigoSuministro').value.trim();

    if(!codigoSuministro) {
        alert("Ingrese el código de suministro antes de continuar.");
        return;
    }

    // Copiar datos de modal 1 al modal cliente
    document.getElementById('empresaTexto').innerText = document.getElementById('empresaHidden').value;
    document.getElementById('servicioTexto').innerText = document.getElementById('servicioHidden').value;
    document.getElementById('codigoTexto').innerText = codigoSuministro;

    $('#sedapalModal').modal('hide');
    $('#modalCliente').modal('show');
}

// --------------------------------
// MODAL 3: Abrir modal de confirmación
// --------------------------------
function abrirConfirmacion() {
    const nombreCliente = document.getElementById('nombre').value.trim();
    const telefono = document.getElementById('telefono').value.trim();
    const monto = document.getElementById('monto').value.trim();
    const descripcion = document.getElementById('descripcion').value.trim();
    const metodoPago = document.getElementById('metodoPago').selectedOptions[0].text;

    if(!nombreCliente || !telefono || !monto || !descripcion) {
        alert("Por favor, complete todos los campos antes de continuar.");
        return;
    }

    const empresa = document.getElementById('empresaTexto').innerText;
    const servicio = document.getElementById('servicioTexto').innerText;
    const codigo = document.getElementById('codigoTexto').innerText;

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
// Guardar cuenta y pago en backend
// --------------------------------
function guardarCuenta() {
    const codigo = document.getElementById('codigoSuministro').value.trim();
    const nombre = document.getElementById('nombre').value.trim();
    const telefono = document.getElementById('telefono').value.trim();
    const monto = document.getElementById('monto').value.trim();
    const descripcion = document.getElementById('descripcion').value.trim();
    const idMetodoPago = document.getElementById('metodoPago').value;

    if(!codigo || !nombre || !telefono || !monto || !descripcion || !idMetodoPago) {
        alert("Por favor, complete todos los campos antes de continuar.");
        return;
    }

    // 1️⃣ Guardar cuenta de suministro
    const cuentaData = new URLSearchParams();
    cuentaData.append("codigoSuministro", codigo);
    cuentaData.append("nombreCliente", nombre);
    cuentaData.append("telefono", telefono);
    cuentaData.append("monto", monto);
    cuentaData.append("serviciosOfrecidos", descripcion);
    cuentaData.append("publicidad", "Publicidad ejemplo");
    cuentaData.append("idServicio", document.getElementById('servicioIdHidden').value);
    cuentaData.append("idMetodoPago", idMetodoPago);

    fetch("/cuenta/registrar", {
        method: "POST",
        body: cuentaData
    })
    .then(res => res.json())
    .then(cuentaResp => {
        console.log("Cuenta guardada:", cuentaResp);

        // 2️⃣ Guardar pago asociado
        const pagoData = {
            idUsuarios: 1, // Cambiar según usuario actual
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

        // 3️⃣ Mostrar modal de comprobante
        const contenido = `
            <div class="row mb-2">
                <div class="col-md-6"><strong>Empresa:</strong> ${pagoResp.nombreEmpresa}</div>
                <div class="col-md-6"><strong>Servicio:</strong> ${pagoResp.servicio}</div>
            </div>
            <div class="row mb-2">
                <div class="col-md-4"><strong>Código Suministro:</strong> ${pagoResp.cliente.codigoSuministro}</div>
                <div class="col-md-4"><strong>Cliente:</strong> ${pagoResp.cliente.nombreCliente}</div>
                <div class="col-md-4"><strong>Teléfono:</strong> ${pagoResp.cliente.telefono}</div>
            </div>
            <div class="row mb-2">
                <div class="col-md-4"><strong>Método Pago:</strong> ${pagoResp.metodoPago}</div>
                <div class="col-md-4"><strong>Monto:</strong> ${pagoResp.monto}</div>
                <div class="col-md-4"><strong>Fecha:</strong> ${new Date(pagoResp.fechaPago).toLocaleString()}</div>
            </div>
            <div class="row mb-2">
                <div class="col-md-6"><strong>Servicios ofrecidos:</strong> ${pagoResp.serviciosOfrecidos}</div>
                <div class="col-md-6"><strong>Publicidad:</strong> ${pagoResp.publicidad}</div>
            </div>
            <div class="row mb-2">
                <div class="col-md-6"><strong>Código Operación:</strong> ${pagoResp.codigoOperacion}</div>
                <div class="col-md-6"><strong>Número Comprobante:</strong> ${pagoResp.numeroComprobante}</div>
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

// --------------------------------
// Reiniciar todos los campos al cerrar modales
// --------------------------------
