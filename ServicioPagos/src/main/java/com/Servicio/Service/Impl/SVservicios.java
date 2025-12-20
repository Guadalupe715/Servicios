package com.Servicio.Service.Impl;

import com.Servicio.Entity.Servicios;


import java.util.List;

public interface SVservicios {
    public List<Servicios> listar();

    public Servicios buscar(Integer idServicios);

    public Servicios agregar(Servicios serv);

    public boolean eliminar(Integer idServicios);

    public List<Servicios>listarEmpresa(Integer idEmpresa);
}
