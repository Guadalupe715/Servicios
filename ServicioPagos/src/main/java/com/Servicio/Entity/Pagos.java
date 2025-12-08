package com.Servicio.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPagos;
    @ManyToOne
    @JoinColumn(name = "idUsuarios")
    private Usuarios usuarios;
    @ManyToOne
    @JoinColumn(name = "idServicios")
    private Servicios servicios;
    @OneToOne
    @JoinColumn(name = "idCuentaSuministro")
    private CuentaSuministro cuentaSuministro;
    @ManyToOne
    @JoinColumn(name = "idMetodoPago")
    private MetodoPago metodoPago;

    private String codigoOperacion;
    private String numeroComprobante;
    private LocalDateTime fechaPago;
}
