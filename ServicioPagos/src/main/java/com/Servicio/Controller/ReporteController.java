package com.Servicio.Controller;

import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;
import com.Servicio.Service.ReportesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReporteController {

    @Autowired
    private ReportesServicios reportesServicios;

    @PostMapping("/generar")
    public ResponseEntity <ReportesResponseDTO> generarReporte(@RequestBody ReportesRequestDTO request) {
        ReportesResponseDTO report = reportesServicios.generarReporte(request);
        if(report == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
    @GetMapping("/{idReportes}")
     public ResponseEntity<ReportesResponseDTO> verDetalles(@PathVariable Integer idReportes){
        ReportesResponseDTO report = reportesServicios.verDetalles(idReportes);
        if(report == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }
}
