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
                request.getFechaInicio().atStartOfDay(), request.getFechaFin().atTime(23, 59, 59)
        );

        Reportes reporte = new Reportes();
        reporte.setFechaInicio(request.getFechaInicio());
        reporte.setFechaFin(request.getFechaFin());
        reporte.setFechaConsulta(LocalDateTime.now());
        Reportes guardar = reportesRepositorio.save(reporte);

        List<DetallesReportesResponseDTO> listaDetalles = new ArrayList<>();
        Map<String, Double> totalPorMetodo = new HashMap<>();

        double totalGenerado = 0;
        for (Pagos p : pagos) {
            DetallesReportes det = new DetallesReportes();
            det.setReportes(reporte);
            det.setPagos(p);
            detallesRepositorio.save(det);

            DetallesReportesResponseDTO dto = new DetallesReportesResponseDTO();
            dto.setIdDetallesReportes(det.getIdDetallesReportes());
            dto.setMonto(p.getCuentaSuministro().getMonto());
            dto.setMetodoPago(p.getMetodoPago().getNombre());
            dto.setFechaPago(p.getFechaPago());
            listaDetalles.add(dto);

            double monto = p.getCuentaSuministro().getMonto();
            String metodo = p.getMetodoPago().getNombre();

            totalGenerado += monto;
            totalPorMetodo.put(metodo, totalPorMetodo.getOrDefault(metodo, 0.0) + monto);


        }
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

        List<DetallesReportesResponseDTO> listaDetalles = new ArrayList<>();
        Map<String, Double> totalPorMetodo = new HashMap<>();
        double totalGenerado = 0;
        for (DetallesReportes det : detalles) {
            double monto = det.getPagos().getCuentaSuministro().getMonto();
            String metodo = det.getPagos().getMetodoPago().getNombre();

            DetallesReportesResponseDTO dto = new DetallesReportesResponseDTO();
            dto.setIdDetallesReportes(det.getIdDetallesReportes());
            dto.setMonto(monto);
            dto.setMetodoPago(metodo);
            dto.setFechaPago(det.getPagos().getFechaPago());
            listaDetalles.add(dto);

            totalGenerado += monto;
            totalPorMetodo.put(metodo, totalPorMetodo.getOrDefault(metodo, 0.0) + monto);
        }


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
