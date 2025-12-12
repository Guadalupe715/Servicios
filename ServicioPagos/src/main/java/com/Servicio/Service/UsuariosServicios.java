package com.Servicio.Service;

import com.Servicio.Entity.Usuarios;
import com.Servicio.Repository.UsuariosRepocitorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosServicios implements SVusuarios{

    @Autowired
    private UsuariosRepocitorio usuariosRepocitorio;

    @Override
    public List<Usuarios> listar() {
        return usuariosRepocitorio.findAll();
    }

    @Override
    public Usuarios buscar(int id) {
        return usuariosRepocitorio.findById(id).orElse(null);
    }

    @Override
    public Usuarios agregar(Usuarios usu) {
        return usuariosRepocitorio.save(usu);
    }

    @Override
    public boolean eliminar(int id) {
        if(usuariosRepocitorio.existsById(id)){
           usuariosRepocitorio.deleteById(id);
           return true;
        }
            return false;
    }
}
