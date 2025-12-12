package com.Servicio.Service.Impl;

import com.Servicio.Entity.MetodoPago;

import java.util.List;

public interface SVmetodoPago {
    public List<MetodoPago> listar();

    public MetodoPago buscar(Integer idMetodo);

    public MetodoPago agregar(MetodoPago meto);

    public boolean eliminar(Integer idMetodo);
}
