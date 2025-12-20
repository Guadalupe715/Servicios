function abrirModalSuministro(servicio) {

    document.getElementById("servicioHidden").value = servicio;
    document.getElementById("tituloServicio").innerText =
        servicio + " - Código de Suministro";

    document.getElementById("codigoSuministro").value = "";

    $('#sedapalModal').modal('show');
}

function continuarCliente() {
    const codigo = document.getElementById("codigoSuministro").value;
    const servicio = document.getElementById("servicioHidden").value;

    if (!codigo) {
        alert("Ingrese el código de suministro");
        return;
    }

    // aquí luego lo usarás en Pagos
    document.getElementById("codigoHidden").value = codigo;

    $('#sedapalModal').modal('hide');
    $('#modalCliente').modal('show');

    // 🔒 PASO DEFINITIVO (queda fijo)
    document.getElementById("servicioTexto").innerText = servicio;
    document.getElementById("codigoTexto").innerText = codigo;

    // para Pagos
    document.getElementById("servicioHiddenCliente").value = servicio;
    document.getElementById("codigoHidden").value = codigo;

    $('#sedapalModal').modal('hide');
    $('#modalCliente').modal('show');
}

