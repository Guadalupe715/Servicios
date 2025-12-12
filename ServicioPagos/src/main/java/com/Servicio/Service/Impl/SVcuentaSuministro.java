package com.Servicio.Service.Impl;

import com.Servicio.DTOresponse.Request.CuentaRequestDTO;
import com.Servicio.DTOresponse.cuentaResponseDTO;

import java.util.List;

public interface SVcuentaSuministro {

    public cuentaResponseDTO crear(CuentaRequestDTO request);

    public List<cuentaResponseDTO> listar();

}
