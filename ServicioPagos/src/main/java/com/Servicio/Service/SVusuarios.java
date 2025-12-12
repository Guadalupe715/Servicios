package com.Servicio.Service;

import com.Servicio.Entity.Usuarios;

import java.util.List;

public interface SVusuarios {
    public List<Usuarios> listar();
    public Usuarios buscar(int id);
    public Usuarios agregar(Usuarios usu);
    public boolean eliminar(int id);
}
