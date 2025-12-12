package com.Servicio.Service.Impl;

import com.Servicio.DTOresponse.CuentaResponseDTO;
import com.Servicio.DTOresponse.Request.CuentaRequestDTO;

import java.util.List;

public interface SVcuentaSuministro {

    public CuentaResponseDTO crear(CuentaRequestDTO request);

    public List<CuentaResponseDTO> listar();

}
