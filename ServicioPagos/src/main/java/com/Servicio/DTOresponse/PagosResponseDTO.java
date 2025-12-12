package com.Servicio.DTOresponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagosResponseDTO {
    private Integer idPagos;
    private String usuarios;
    private String servicios;
    private String cuentaSuministro;
    private String metodoPago;
    private String codigoOperacion;
    private String numeroComprobante;
    private LocalDateTime fechaPago;
}
