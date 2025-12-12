package com.Servicio.Service;

import com.Servicio.DTOresponse.Request.CuentaRequestDTO;
import com.Servicio.DTOresponse.cuentaResponseDTO;
import com.Servicio.Entity.CuentaSuministro;
import com.Servicio.Entity.MetodoPago;
import com.Servicio.Entity.Servicios;
import com.Servicio.Repository.CuentaSuministroRepositorio;
import com.Servicio.Repository.MetodoPagoRepositorio;
import com.Servicio.Repository.ServiciosRepocitorio;
import com.Servicio.Service.Impl.SVcuentaSuministro;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CuentaServicios implements SVcuentaSuministro {
    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;
    @Autowired
    private ServiciosRepocitorio serviciosRepocitorio;
    @Autowired
    private CuentaSuministroRepositorio cuentaSuministroRepositorio;

    @Override
    public cuentaResponseDTO crear(CuentaRequestDTO request) {
        Servicios servicio = serviciosRepocitorio.findById(request.getIdServicio()).orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        MetodoPago metodoPago = metodoPagoRepositorio.findById(request.getIdServicio()).orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado"));

        CuentaSuministro cuenta = new CuentaSuministro();
        cuenta.setCodigoSuministro(request.getCodigoSuministro());
        cuenta.setMonto(request.getMonto());
        cuenta.setNombreCliente(request.getNombreCliente());
        cuenta.setTelefono(request.getTelefono());
        cuenta.setServicios(servicio);
        cuenta.setMetodoPago(metodoPago);

        CuentaSuministro guardar = cuentaSuministroRepositorio.save(cuenta);

        return new cuentaResponseDTO(
                guardar.getIdCuentaSuministro(),
                guardar.getCodigoSuministro(),
                guardar.getNombreCliente(),
                guardar.getTelefono(),
                guardar.getMonto(),
                servicio.getNombre(),
                metodoPago.getNombre()
        );

    }

    @Override
    public List<cuentaResponseDTO> listar() {
        return cuentaSuministroRepositorio.findAll().stream()
                .map(c -> new cuentaResponseDTO(
                        c.getIdCuentaSuministro(),
                        c.getCodigoSuministro(),
                        c.getNombreCliente(),
                        c.getTelefono(),
                        c.getMonto(),
                        c.getServicios().getNombre(),
                        c.getMetodoPago().getNombre()
                ))
                .collect(Collectors.toList());
    }
}
