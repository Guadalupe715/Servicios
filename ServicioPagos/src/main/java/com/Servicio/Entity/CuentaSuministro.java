package com.Servicio.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private  String codigoSuministro;
    private String nombreCliente;
    private double monto;
    private String telefono;
}
