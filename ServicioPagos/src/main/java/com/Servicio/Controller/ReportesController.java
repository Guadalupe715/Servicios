package com.Servicio.Controller;

import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;
import com.Servicio.Service.ReportesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReportesController {
    @Autowired
    private ReportesServicios reportesService;
    @PostMapping("/generar")
    public ResponseEntity<ReportesResponseDTO> generarReporte(@RequestBody ReportesRequestDTO request) {
        ReportesResponseDTO reporte = reportesService.generarReporte(request);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte);
    }
    @GetMapping("/{idReportes}")
    public ResponseEntity<ReportesResponseDTO> verReporte(@PathVariable Integer idReportes) {
        ReportesResponseDTO reporte = reportesService.verDetalles(idReportes);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte);
    }

}
