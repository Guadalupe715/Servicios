package com.Servicio.Service;

import com.Servicio.Entity.Servicios;
import com.Servicio.Repository.ServiciosRepocitorio;
import com.Servicio.Service.Impl.SVservicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosServicios implements SVservicios {
    @Autowired
    private ServiciosRepocitorio serviciosRepocitorio;

    @Override
    public List<Servicios> listar() {
        return serviciosRepocitorio.findAll();
    }

    @Override
    public Servicios buscar(Integer idServicios) {
        return serviciosRepocitorio.findById(idServicios).orElse(null);
    }

    @Override
    public Servicios agregar(Servicios serv) {
        return serviciosRepocitorio.save(serv);
    }

    @Override
    public boolean eliminar(Integer idServicios) {
        if (serviciosRepocitorio.existsById(idServicios)) {
            serviciosRepocitorio.deleteById(idServicios);
            return true;
        }
        return false;
    }

    @Override
    public List<Servicios> listarEmpresa(Integer idEmpresa) {
        return serviciosRepocitorio.findByEmpresaIdEmpresa(idEmpresa);
    }
}
