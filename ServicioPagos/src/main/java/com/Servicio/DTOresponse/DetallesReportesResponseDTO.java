package com.Servicio.DTOresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallesReportesResponseDTO {
    private Integer idDetallesReportes;
    private double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
}
