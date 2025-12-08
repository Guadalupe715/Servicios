package com.Servicio.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reportes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReportes;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDateTime fechaConsulta;
    @OneToMany (mappedBy = "reportes")
    private List<detallesReportes> detallesReportes;
}
