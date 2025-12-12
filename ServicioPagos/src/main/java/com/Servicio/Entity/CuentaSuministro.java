package com.Servicio.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaSuministro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCuentaSuministro;
    private String codigoSuministro;
    private String nombreCliente;
    private double monto;
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "idServicios")
    private Servicios servicios;
    @ManyToOne
    @JoinColumn(name = "idMetodoPago")
    private MetodoPago metodoPago;
}
