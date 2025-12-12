package com.Servicio.DTOresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDTO {
    private Integer idCuentaSuministro;
    private String codigoSuministro;
    private String nombreCliente;
    private String telefono;
    private double monto;
    private String nombreServicio;
    private String nombreMetodoPago;
}
