package com.Servicio.Service;

import com.Servicio.DTOresponse.CuentaResponseDTO;
import com.Servicio.DTOresponse.Request.CuentaRequestDTO;
import com.Servicio.Entity.CuentaSuministro;
import com.Servicio.Entity.MetodoPago;
import com.Servicio.Entity.Servicios;
import com.Servicio.Repository.CuentaSuministroRepositorio;
import com.Servicio.Repository.MetodoPagoRepositorio;
import com.Servicio.Repository.ServiciosRepocitorio;
import com.Servicio.Service.Impl.SVcuentaSuministro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServicios implements SVcuentaSuministro {
    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;
    @Autowired
    private ServiciosRepocitorio serviciosRepocitorio;
    @Autowired
    private CuentaSuministroRepositorio cuentaSuministroRepositorio;

    @Override
    public CuentaResponseDTO crear(CuentaRequestDTO request) {
        Servicios servicio = serviciosRepocitorio.findById(request.getIdServicio()).orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        MetodoPago metodoPago = metodoPagoRepositorio.findById(request.getIdMetodoPago()).orElseThrow(() -> new RuntimeException("Metodo de pago no encontrado"));

        CuentaSuministro cuenta = new CuentaSuministro();
        cuenta.setCodigoSuministro(request.getCodigoSuministro());
        cuenta.setNombreCliente(request.getNombreCliente());
        cuenta.setTelefono(request.getTelefono());
        cuenta.setMonto(request.getMonto());
        cuenta.setServiciosOfrecidos(request.getServiciosOfrecidos());
        cuenta.setPublicidad(request.getPublicidad());
        cuenta.setServicios(servicio);
        cuenta.setMetodoPago(metodoPago);

        CuentaSuministro guardar = cuentaSuministroRepositorio.save(cuenta);

        return new CuentaResponseDTO(
                guardar.getIdCuentaSuministro(),
                guardar.getNombreEmpresa(),
                guardar.getDireccion(),
                guardar.getCodigoSuministro(),
                guardar.getNombreCliente(),
                guardar.getTelefono(),
                guardar.getMonto(),
                servicio.getNombre(),
                metodoPago.getNombre(),
                guardar.getServiciosOfrecidos(),
                guardar.getPublicidad()
        );

    }

    @Override
    public List<CuentaResponseDTO> listar() {
        return cuentaSuministroRepositorio.findAll().stream()
                .map(c -> new CuentaResponseDTO(
                        c.getIdCuentaSuministro(),
                        c.getCodigoSuministro(),
                        c.getNombreEmpresa(),
                        c.getDireccion(),
                        c.getNombreCliente(),
                        c.getTelefono(),
                        c.getMonto(),
                        c.getServicios().getNombre(),
                        c.getMetodoPago().getNombre(),
                        c.getPublicidad(),
                        c.getServiciosOfrecidos()
                ))
                .collect(Collectors.toList());
    }
}
