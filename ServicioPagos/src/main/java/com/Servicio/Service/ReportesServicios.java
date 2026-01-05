package com.Servicio.Service;

import com.Servicio.DTOresponse.DetallesReportesResponseDTO;
import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;
import com.Servicio.Entity.DetallesReportes;
import com.Servicio.Entity.Pagos;
import com.Servicio.Entity.Reportes;
import com.Servicio.Repository.DetallesRepositorio;
import com.Servicio.Repository.PagosRepocitorio;
import com.Servicio.Repository.ReportesRepositorio;
import com.Servicio.Service.Impl.SVreportes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class ReportesServicios implements SVreportes {

    @Autowired
    private PagosRepocitorio pagosRepocitorio;
    @Autowired
    private ReportesRepositorio reportesRepositorio;
    @Autowired
    private DetallesRepositorio detallesRepositorio;


    @Override
    public ReportesResponseDTO generarReporte(ReportesRequestDTO request) {
        List<Pagos> pagos = pagosRepocitorio.findByFechaPagoBetween(
                request.getFechaInicio().atStartOfDay(),
                request.getFechaFin().atTime(23, 59, 59)
        );

        Reportes reporte = new Reportes();
        reporte.setFechaInicio(request.getFechaInicio());
        reporte.setFechaFin(request.getFechaFin());
        reporte.setFechaConsulta(LocalDateTime.now());
        Reportes guardar = reportesRepositorio.save(reporte);

        List<DetallesReportes> detalles = pagos.stream().map(p -> {
            DetallesReportes det = new DetallesReportes();
            det.setReportes(guardar);
            det.setPagos(p);
            return det;

        }).collect(Collectors.toList());

        detallesRepositorio.saveAll(detalles);

        List<DetallesReportesResponseDTO> listaDetalles = detalles.stream()
                .map(det -> {
                    Pagos p = det.getPagos();
                    return new DetallesReportesResponseDTO(
                            det.getIdDetallesReportes(),
                            p.getCuentaSuministro().getCodigoSuministro(),
                            p.getCuentaSuministro().getNombreCliente(),
                            p.getServicios().getNombre(),
                            p.getCuentaSuministro().getMonto(),
                            p.getMetodoPago().getNombre(),
                            p.getFechaPago()
                    );
                }).collect(Collectors.toList());

        Map<String, Double> totalPorMetodo = listaDetalles.stream()
                .collect(Collectors.groupingBy(
                        DetallesReportesResponseDTO::getMetodoPago,
                        Collectors.summingDouble(DetallesReportesResponseDTO::getMonto)
                ));

        double totalGenerado = listaDetalles.stream()
                .mapToDouble(DetallesReportesResponseDTO::getMonto)
                .sum();

        return new ReportesResponseDTO(
                guardar.getIdReportes(),
                guardar.getFechaInicio(),
                guardar.getFechaFin(),
                guardar.getFechaConsulta(),
                listaDetalles,
                totalGenerado,
                totalPorMetodo
        );
    }

    @Override
    public ReportesResponseDTO verDetalles(Integer idReportes) {

        Reportes reporte = reportesRepositorio.findById(idReportes)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        List<DetallesReportes> detalles = detallesRepositorio.findByReportesIdReportes(idReportes);

        List<DetallesReportesResponseDTO> listaDetalles = detalles.stream()
                .map(det -> {
                    Pagos p = det.getPagos();
                    return new DetallesReportesResponseDTO(
                            det.getIdDetallesReportes(),
                            p.getCuentaSuministro().getCodigoSuministro(),
                            p.getCuentaSuministro().getNombreCliente(),
                            p.getServicios().getNombre(),
                            p.getCuentaSuministro().getMonto(),
                            p.getMetodoPago().getNombre(),
                            p.getFechaPago()
                            );
                }).collect(Collectors.toList());

        Map<String, Double> totalPorMetodo = listaDetalles.stream()
                .collect(Collectors.groupingBy(
                        DetallesReportesResponseDTO::getMetodoPago,
                        Collectors.summingDouble(DetallesReportesResponseDTO::getMonto)
                ));


        double totalGenerado = listaDetalles.stream()
                .mapToDouble(DetallesReportesResponseDTO::getMonto)
                .sum();


        return new ReportesResponseDTO(
                reporte.getIdReportes(),
                reporte.getFechaInicio(),
                reporte.getFechaFin(),
                reporte.getFechaConsulta(),
                listaDetalles,
                totalGenerado,
                totalPorMetodo
        );

    }

    public void generarReportePDF(LocalDate fechaInicio, LocalDate fechaFin, HttpServletResponse response)
            throws IOException, DocumentException {

        // 🔹 Reutilizas tu lógica existente
        ReportesRequestDTO request = new ReportesRequestDTO();
        request.setFechaInicio(fechaInicio);
        request.setFechaFin(fechaFin);

        ReportesResponseDTO reporte = generarReporte(request);

        // 🔹 Configurar respuesta
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
        document.add(new Paragraph(
                "Total generado: S/ " + reporte.getTotalGeneral()
        ));

        document.close();
    }
}
