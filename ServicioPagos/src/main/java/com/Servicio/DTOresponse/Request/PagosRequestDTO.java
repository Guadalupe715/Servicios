package com.Servicio.DTOresponse.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagosRequestDTO {
    private Integer idUsuarios;
    private Integer idServicios;
    private Integer idMetodo;
    private Integer idCuenta;
}
