package com.Servicio.Service;

import com.Servicio.Entity.Usuarios;
import com.Servicio.Repository.UsuariosRepocitorio;
import com.Servicio.Service.Impl.SVusuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosServicios implements SVusuarios {

    @Autowired
    private UsuariosRepocitorio usuariosRepocitorio;

    @Override
    public List<Usuarios> listar() {
        return usuariosRepocitorio.findAll();
    }

    @Override
    public Usuarios buscar(Integer idUsuarios) {
        return usuariosRepocitorio.findById(idUsuarios).orElse(null);
    }

    @Override
    public Usuarios agregar(Usuarios usu) {
        return usuariosRepocitorio.save(usu);
    }

    @Override
    public boolean eliminar(Integer idUsuarios) {
        if(usuariosRepocitorio.existsById(idUsuarios)){
           usuariosRepocitorio.deleteById(idUsuarios);
           return true;
        }
            return false;
    }
}
