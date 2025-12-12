package com.Servicio.Service.Impl;

import com.Servicio.Entity.Usuarios;

import java.util.List;

public interface SVusuarios {
    public List<Usuarios> listar();
    public Usuarios buscar(Integer idUsuarios);
    public Usuarios agregar(Usuarios usu);
    public boolean eliminar(Integer idUsuarios);
}
