package com.Servicio.Repository;

import com.Servicio.Entity.CuentaSuministro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaSuministroRepositorio extends JpaRepository<CuentaSuministro,Integer> {
}
