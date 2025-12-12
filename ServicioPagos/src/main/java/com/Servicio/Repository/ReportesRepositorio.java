package com.Servicio.Repository;

import com.Servicio.Entity.Reportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportesRepositorio extends JpaRepository<Reportes,Integer> {

}
