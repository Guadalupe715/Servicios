package com.Servicio.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idServicios;
    private String nombre;
    private String icono;
    private String color;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
}
