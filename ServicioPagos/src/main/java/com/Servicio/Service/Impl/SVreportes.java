package com.Servicio.Service.Impl;

import com.Servicio.DTOresponse.DetallesReportesResponseDTO;
import com.Servicio.DTOresponse.ReportesResponseDTO;
import com.Servicio.DTOresponse.Request.ReportesRequestDTO;

public interface SVreportes {
    public ReportesResponseDTO generarReporte(ReportesRequestDTO request);
    public ReportesResponseDTO verDetalles(Integer idReportes);
}
