package com.Servicio.Security;

import com.Servicio.Entity.Usuarios;
import com.Servicio.Repository.UsuariosRepocitorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class usuarioDetalles implements UserDetailsService {
    @Autowired
    private UsuariosRepocitorio usuariosRepocitorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = usuariosRepocitorio.findByUsuario(username)
                .orElseThrow(()->new UsernameNotFoundException("Usuario no Existe"));
        return new UsuarioPrincipal(usuarios);
    }
}
