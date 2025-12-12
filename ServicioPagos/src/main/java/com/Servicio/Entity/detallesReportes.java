package com.Servicio.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class detallesReportes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetallesReportes;
    @ManyToOne
    @JoinColumn(name = "idReportes")
    private Reportes reportes;
    @ManyToOne
    @JoinColumn(name = "idPagos")
    private Pagos pagos;
}
