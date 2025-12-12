package com.Servicio.Service.Impl;

import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;

public interface SVpagos {
    PagosResponseDTO crearPago (PagosRequestDTO request);
}
