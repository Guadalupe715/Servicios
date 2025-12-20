package com.Servicio.Repository;

import com.Servicio.Entity.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiciosRepocitorio extends JpaRepository<Servicios,Integer>{

    List<Servicios>findByEmpresaIdEmpresa(Integer idEmpresa);
}
