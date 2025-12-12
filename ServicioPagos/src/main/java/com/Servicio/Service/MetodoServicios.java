package com.Servicio.Service;

import com.Servicio.Entity.MetodoPago;
import com.Servicio.Repository.MetodoPagoRepositorio;
import com.Servicio.Service.Impl.SVmetodoPago;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MetodoServicios implements SVmetodoPago {
    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;
    @Override
    public List<MetodoPago> listar() {
        return metodoPagoRepositorio.findAll();
    }

    @Override
    public MetodoPago buscar(Integer idMetodo) {
        return metodoPagoRepositorio.findById(idMetodo).orElse(null);
    }

    @Override
    public MetodoPago agregar(MetodoPago meto) {
        return metodoPagoRepositorio.save(meto);
    }

    @Override
    public boolean eliminar(Integer idMetodo) {
        if(metodoPagoRepositorio.existsById(idMetodo)){
            metodoPagoRepositorio.deleteById(idMetodo);
            return true;
        }
        return false;
    }
}
