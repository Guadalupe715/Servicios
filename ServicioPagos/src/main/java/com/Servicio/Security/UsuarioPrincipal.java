package com.Servicio.Security;

import com.Servicio.Entity.Usuarios;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UsuarioPrincipal implements UserDetails {

    private String nombre;
    private String email;
    private String usuario;
    private String password;
    private String rol;

    public UsuarioPrincipal(Usuarios usuario) {
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.usuario = usuario.getUsuario();
        this.password = usuario.getPassword();
        this.rol = usuario.getRol();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertimos a mayúsculas y aseguramos que empiece con ROLE_
        String r = rol.toUpperCase();
        if (!r.startsWith("ROLE_")) {
            r = "ROLE_" + r;
        }
        return Collections.singletonList(new SimpleGrantedAuthority(r));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    // Implementa los demás métodos de UserDetails según tus necesidades
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // Getters adicionales para Thymeleaf
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }


}
