package com.Servicio.DTOresponse.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDTO {
    private String codigoSuministro;
    private String nombreCliente;
    private String telefono;
    private double monto;
    private String serviciosOfrecidos;
    private String publicidad;
    private Integer idServicio;
    private Integer idMetodoPago;
}
