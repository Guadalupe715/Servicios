package com.Servicio.DTOresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDTO {
    private Integer idCuentaSuministro;
    private String nombreEmpresa; // esto es de mi empresa neuro
    private String direccion;
    private String codigoSuministro;
    private String nombreCliente;
    private String telefono;
    private double monto;
    private String nomEmpresa; // Esto es de sedapal entre otros
    private String nombreServicio;
    private String nombreMetodoPago;
    private String serviciosOfrecidos;
    private String publicidad;
}
