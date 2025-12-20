package com.Servicio.Service;

import com.Servicio.Entity.Empresa;
import com.Servicio.Repository.EmpresaRepositorio;
import com.Servicio.Service.Impl.SVempresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpresaServicios implements SVempresa {
    @Autowired
    private EmpresaRepositorio empresaRepositorio;
    @Override
    public List<Empresa> listar() {
        return empresaRepositorio.findAll();
    }

    @Override
    public Empresa buscar(Integer idEmpresa) {
        return empresaRepositorio.findById(idEmpresa).orElse(null);
    }

    @Override
    public Empresa agregar(Empresa emp) {
        return empresaRepositorio.save(emp);
    }

    @Override
    public boolean eliminar(Integer idEmpresa) {
        if (empresaRepositorio.existsById(idEmpresa)){
            empresaRepositorio.deleteById(idEmpresa);
            return true ;
        }
        return false;
    }
}
