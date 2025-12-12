package com.Servicio.Service;

import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;
import com.Servicio.Entity.*;
import com.Servicio.Repository.*;
import com.Servicio.Service.Impl.SVpagos;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class PagosServicios implements SVpagos {
    @Autowired
    private UsuariosRepocitorio usuariosRepocitorio;
    @Autowired
    private CuentaSuministroRepositorio cuentaSuministroRepositorio;
    @Autowired
    private ServiciosRepocitorio serviciosRepocitorio;
    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;
    @Autowired
    private PagosRepocitorio pagosRepocitorio;

    @Override
    public PagosResponseDTO crearPago(PagosRequestDTO request) {
        Usuarios usuarios = usuariosRepocitorio.findById(request.getIdUsuarios())
                .orElseThrow(() -> new RuntimeException("Usuario no Encontrado"));
        Servicios servicios = serviciosRepocitorio.findById(request.getIdServicios())
                .orElseThrow(() -> new RuntimeException("Servicio no Encontrado"));
        CuentaSuministro cuentaSuministro = cuentaSuministroRepositorio.findById(request.getIdCuenta())
                .orElseThrow(() -> new RuntimeException("Cuenta no Encontrado"));
        MetodoPago metodoPago = metodoPagoRepositorio.findById(request.getIdMetodo())
                .orElseThrow(() -> new RuntimeException("Metodo no Encontrado"));

        Pagos pagos = new Pagos();
        pagos.setUsuarios(usuarios);
        pagos.setServicios(servicios);
        pagos.setMetodoPago(metodoPago);
        pagos.setCuentaSuministro(cuentaSuministro);
        pagos.setFechaPago(LocalDateTime.now());

    }
}
