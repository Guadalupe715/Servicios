    function verReporte(btn) {
        let id = btn.getAttribute("data-id");

        fetch(`/report/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("Error al cargar reporte");
                return res.json();
            })
            .then(data => {

                // Fechas
                document.getElementById("mFechaInicio").innerText = data.fechaInicio;
                document.getElementById("mFechaFin").innerText = data.fechaFin;
                document.getElementById("mTotal").innerText =
                    "S/ " + data.totalGenerado.toFixed(2);

                // Tabla
                let tbody = document.getElementById("tablaDetalle");
                tbody.innerHTML = "";

                data.detalles.forEach((d, i) => {
                    let fila = `
                        <tr>
                            <td>${i + 1}</td>
                            <td>${d.codigoSuministro}</td>
                            <td>${d.nombreCliente}</td>
                            <td>${d.servicio}</td>
                            <td>S/ ${d.monto.toFixed(2)}</td>
                            <td>${d.metodoPago}</td>
                            <td>${d.fechaPago}</td>
                        </tr>
                    `;
                    tbody.innerHTML += fila;
                });

                // Mostrar modal
                let modal = new bootstrap.Modal(
                    document.getElementById("modalReporte")
                );
                modal.show();
            })
            .catch(err => {
                alert("No se pudo cargar el reporte");
                console.error(err);
            });
    }

