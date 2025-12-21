// Abrir modal de código de suministro
function abrirModalSuministro(nombreServicio, nombreEmpresa) {
    document.getElementById("tituloServicio").innerText = nombreEmpresa + " - " + nombreServicio;
    document.getElementById("empresaHidden").value = nombreEmpresa;
    document.getElementById("servicioHidden").value = nombreServicio;
    document.getElementById("codigoSuministro").value = "";
    $('#sedapalModal').modal('show');
}

// Continuar al modal cliente
function continuarCliente() {
    const codigo = document.getElementById("codigoSuministro").value;
    if (!codigo) { alert("Ingrese código de suministro"); return; }

    document.getElementById("empresaTexto").innerText = document.getElementById("empresaHidden").value;
    document.getElementById("servicioTexto").innerText = document.getElementById("servicioHidden").value;
    document.getElementById("codigoTexto").innerText = codigo;

    $('#sedapalModal').modal('hide');
    $('#modalCliente').modal('show');
}

// Abrir modal de confirmación
function abrirConfirmacion() {
    const nombre = document.getElementById('nombre').value;
    const telefono = document.getElementById('telefono').value;
    const metodoPagoSelect = document.getElementById('metodoPago');
    const metodoPagoText = metodoPagoSelect.selectedOptions[0].text;
    const monto = document.getElementById('monto').value;
    const descripcion = document.getElementById('descripcion').value;

    const empresa = document.getElementById('empresaTexto').innerText;
    const servicio = document.getElementById('servicioTexto').innerText;
    const codigo = document.getElementById('codigoTexto').innerText;

    const contenido = `
        <p><strong>Empresa:</strong> ${empresa}</p>
        <p><strong>Servicio:</strong> ${servicio}</p>
        <p><strong>Código:</strong> ${codigo}</p>
        <p><strong>Cliente:</strong> ${nombre}</p>
        <p><strong>Teléfono:</strong> ${telefono}</p>
        <p><strong>Monto:</strong> ${monto}</p>
        <p><strong>Método de Pago:</strong> ${metodoPagoText}</p>
        <p><strong>Servicios Ofrecidos:</strong> ${descripcion}</p>
    `;
    document.getElementById('contenidoConfirmacion').innerHTML = contenido;

    $('#modalCliente').modal('hide');
    $('#modalConfirmacion').modal('show');
}

function volverAlCliente() {
    $('#modalConfirmacion').modal('hide');
    $('#modalCliente').modal('show');
}

// Guardar la cuenta en la base de datos usando fetch/ajax
function guardarCuenta() {
    const data = {
        empresa: document.getElementById('empresaTexto').innerText,
        servicio: document.getElementById('servicioTexto').innerText,
        codigoSuministro: document.getElementById('codigoTexto').innerText,
        nombreCliente: document.getElementById('nombre').value,
        telefono: document.getElementById('telefono').value,
        monto: document.getElementById('monto').value,
        idMetodoPago: document.getElementById('metodoPago').value,
        serviciosOfrecidos: document.getElementById('descripcion').value,
        publicidad: "" // si quieres agregar publicidad
    };

    fetch('/cuenta/registrar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(res => {
        alert("Cuenta registrada correctamente");
        $('#modalConfirmacion').modal('hide');
    })
    .catch(err => {
        console.error(err);
        alert("Error al registrar la cuenta");
    });
}
