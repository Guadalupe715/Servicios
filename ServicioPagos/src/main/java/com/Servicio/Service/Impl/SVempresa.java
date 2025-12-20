package com.Servicio.Service.Impl;


import com.Servicio.Entity.Empresa;

import java.util.List;

public interface SVempresa {
    public List<Empresa> listar();

    public Empresa buscar(Integer idEmpresa);

    public Empresa agregar(Empresa emp);

    public boolean eliminar(Integer idEmpresa);
}
