package com.Servicio.DTOresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {
    private String nombreCliente;
    private String telefono;
    private String codigoSuministro;
}
