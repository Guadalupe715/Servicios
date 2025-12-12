package com.Servicio.Service;

import com.Servicio.Entity.Servicios;
import com.Servicio.Entity.Usuarios;
import com.Servicio.Repository.ServiciosRepocitorio;
import com.Servicio.Repository.UsuariosRepocitorio;
import com.Servicio.Service.Impl.SVservicios;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        if(serviciosRepocitorio.existsById(idServicios)){
            serviciosRepocitorio.deleteById(idServicios);
            return true;
        }
        return false;
    }
}
