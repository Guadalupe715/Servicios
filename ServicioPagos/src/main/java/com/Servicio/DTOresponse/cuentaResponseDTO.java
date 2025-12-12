package com.Servicio.DTOresponse;

import lombok.Data;

@Data
public class cuentaResponseDTO {
    private Integer idCuentaSuministro;
    private String codigoSuministro;
    private String nombreCliente;
    private String telefono;
    private double monto;
    private String nombreServicio;
    private String nombreMetodoPago;
}
