package com.Servicio.Controller;

import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;
import com.Servicio.Entity.Reportes;
import com.Servicio.Repository.ReportesRepositorio;
import com.Servicio.Service.ReportesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReporteController {

    @Autowired
    private ReportesServicios reportesServicios;
    @Autowired
    private ReportesRepositorio reportesRepositorio;

    @GetMapping("/ver-detalle/{id}")
    public String verDetalleEnVista(
            @PathVariable Integer id,
            Model model
    ) {

        ReportesResponseDTO reporte =
                reportesServicios.verDetalles(id);


        model.addAttribute("reportes", reportesRepositorio.findAll());

        model.addAttribute("reporteTemporal", reporte);
        model.addAttribute("detallesTemporales", reporte.getDetalles());
        model.addAttribute("totalGenerado", reporte.getTotalGeneral());
        model.addAttribute("totalesPorMetodo", reporte.getTotalPorMetodo());

        return "reporte";
    }

    @GetMapping("/ver")
    public String verReportes(Model model) {
        List<Reportes> reportes = reportesRepositorio.findAll();
        model.addAttribute("reportes", reportes);
        return "reporte";
    }

    @GetMapping("/consultar")
    public String consultarReporte(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model
    ) {

        ReportesRequestDTO request = new ReportesRequestDTO();
        request.setFechaInicio(fechaInicio);
        request.setFechaFin(fechaFin);

        ReportesResponseDTO reporteTemporal =
                reportesServicios.generarReporte(request);

        List<Reportes> reportes = reportesRepositorio.findAll();
        model.addAttribute("reportes", reportes);

        model.addAttribute("reporteTemporal", reporteTemporal);
        model.addAttribute("detallesTemporales", reporteTemporal.getDetalles());
        model.addAttribute("totalGenerado", reporteTemporal.getTotalGeneral());
        model.addAttribute("totalMetodo", reporteTemporal.getTotalPorMetodo());
        return "reporte";
    }

}
