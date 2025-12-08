package com.Servicio.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {
   private Integer idUsuario;
    private String nombre;
    private String usuario;
    private String email;
    private String rol;
}
