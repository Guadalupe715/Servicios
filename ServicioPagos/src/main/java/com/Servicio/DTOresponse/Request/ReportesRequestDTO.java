package com.Servicio.DTOresponse.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportesRequestDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
