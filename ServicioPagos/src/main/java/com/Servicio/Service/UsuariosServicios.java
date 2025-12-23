package com.Servicio.Service;

import com.Servicio.Entity.Usuarios;
import com.Servicio.Repository.UsuariosRepocitorio;
import com.Servicio.Security.SeguridadConfig;
import com.Servicio.Service.Impl.SVusuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosServicios implements SVusuarios {

    @Autowired
    private UsuariosRepocitorio usuariosRepocitorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<Usuarios> listar() {
        return usuariosRepocitorio.findAll();
    }

    @Override
    public Usuarios buscar(Integer idUsuarios) {
        return usuariosRepocitorio.findById(idUsuarios).orElse(null);
    }

    public Usuarios agregar(Usuarios usu) {
        if (usu.getPassword() != null && !usu.getPassword().isEmpty()) {
            usu.setPassword(passwordEncoder.encode(usu.getPassword()));
        } else if (usu.getIdUsuario() != null) {
            Usuarios existente = usuariosRepocitorio.findById(usu.getIdUsuario()).orElse(null);
            if (existente != null) {
                usu.setPassword(existente.getPassword());
            }
        }
        return usuariosRepocitorio.save(usu);
    }

    @Override
    public boolean eliminar(Integer idUsuarios) {
        if (usuariosRepocitorio.existsById(idUsuarios)) {
            usuariosRepocitorio.deleteById(idUsuarios);
            return true;
        }
        return false;
    }

    @Override
    public Usuarios usuarioLogin(String user) {
        return usuariosRepocitorio.findByUsuario(user).orElse(null);
    }
}
