package com.Servicio.Repository;

import com.Servicio.Entity.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiciosRepocitorio extends JpaRepository<Servicios,Integer>{
}
