package com.Servicio.Controller;

import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;
import com.Servicio.Entity.Reportes;
import com.Servicio.Repository.ReportesRepositorio;
import com.Servicio.Service.ReportesServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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
    public String verDetalleEnVista(@PathVariable Integer id, Model model) {

        ReportesResponseDTO reporte = reportesServicios.verDetalles(id);
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

    @GetMapping("/pdf")
    public void descargarPDF(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            HttpServletResponse response
    ) throws IOException, DocumentException {

        // 🔹 Generar el mismo reporte que ya usas en pantalla
        ReportesRequestDTO request = new ReportesRequestDTO();
        request.setFechaInicio(fechaInicio);
        request.setFechaFin(fechaFin);

        ReportesResponseDTO reporte = reportesServicios.generarReporte(request);

        // 🔹 Configurar descarga
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=reporte_pagos.pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // 🔹 Título
        Font titulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph p = new Paragraph("REPORTE DE PAGOS", titulo);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Desde: " + fechaInicio));
        document.add(new Paragraph("Hasta: " + fechaFin));
        document.add(new Paragraph(" "));

        // 🔹 Tabla
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        String[] headers = {
                "#", "Suministro", "Cliente", "Servicio",
                "Monto", "Método", "Fecha Pago"
        };

        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        int i = 1;
        for (var d : reporte.getDetalles()) {
            table.addCell(String.valueOf(i++));
            table.addCell(d.getCodigoSuministro());
            table.addCell(d.getNombreCliente());
            table.addCell(d.getServicio());
            table.addCell("S/ " + d.getMonto());
            table.addCell(d.getMetodoPago());
            table.addCell(d.getFechaPago().toString());
        }

        document.add(table);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total generado: S/ " + reporte.getTotalGeneral()));

        document.close();
    }

}
