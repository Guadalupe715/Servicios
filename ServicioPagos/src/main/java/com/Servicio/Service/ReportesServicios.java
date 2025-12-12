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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
}
