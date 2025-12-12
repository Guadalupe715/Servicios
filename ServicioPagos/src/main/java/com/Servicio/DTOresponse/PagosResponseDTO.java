package com.Servicio.DTOresponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagosResponseDTO {
    private String nombreEmpresa;
    private String direccion;
    private String usuario;

    private ClienteResponseDTO cliente;
    private String codigoOperacion;
    private String numeroComprobante;
    private LocalDateTime fechaPago;
    private String servicio;
    private double monto;
    private String metodoPago;

    private String serviciosOfrecidos;
    private String publicidad;
}
