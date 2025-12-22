package com.Servicio.Controller;

import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;
import com.Servicio.Entity.Reportes;
import com.Servicio.Repository.ReportesRepositorio;
import com.Servicio.Service.ReportesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReporteController {

    @Autowired
    private ReportesServicios reportesServicios;
    @Autowired
    private ReportesRepositorio reportesRepositorio;

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
    @GetMapping("/ver")
    public String verReportes(Model model){
        List<Reportes> reportes = reportesRepositorio.findAll();
        model.addAttribute("reportes",reportes);
        return "reporte";
    }
}
