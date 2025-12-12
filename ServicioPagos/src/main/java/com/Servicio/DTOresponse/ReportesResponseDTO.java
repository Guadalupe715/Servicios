package com.Servicio.DTOresponse;

import com.Servicio.Entity.Reportes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportesResponseDTO {
    private Reportes reporte;
    private List<DetallesReportesResponseDTO> detalles;
    private double totalGeneral;
    private Map<String, Double> totalPorMetodo;
    private Map<String, Integer> cantidadPorMetodo;
}
