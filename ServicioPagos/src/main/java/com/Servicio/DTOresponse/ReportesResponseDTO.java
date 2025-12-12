package com.Servicio.DTOresponse;

import com.Servicio.Entity.Reportes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportesResponseDTO {
    private Integer idReportes;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaConsulta;
    private List<DetallesReportesResponseDTO> detalles;
    private double totalGeneral;
    private Map<String, Double> totalPorMetodo;
}
