package com.Servicio.Repository;

import com.Servicio.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepocitorio extends JpaRepository<Usuarios,Integer> {

}
