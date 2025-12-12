package com.Servicio.Repository;

import com.Servicio.Entity.DetallesReportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallesRepositorio extends JpaRepository<DetallesReportes, Integer> {
    List<DetallesReportes> findByReportesIdReportes(Integer idReportes);
}
