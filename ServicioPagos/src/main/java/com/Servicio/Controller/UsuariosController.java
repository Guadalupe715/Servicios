package com.Servicio.Controller;

import com.Servicio.Entity.Usuarios;
import com.Servicio.Service.UsuariosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UsuariosController {
    @Autowired
    private UsuariosServicios usuariosServicios;

    @GetMapping
    public ResponseEntity<List<Usuarios>> ListarUsuario() {
        List<Usuarios> usuario = usuariosServicios.listar();
        if (usuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/{idUsuarios}")
    public ResponseEntity<Usuarios> BuscarUsuarioId(@PathVariable Integer idUsuarios) {
        Usuarios usu = usuariosServicios.buscar(idUsuarios);
        if (usu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usu);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Usuarios> AgregarUsuarios(@RequestBody Usuarios usu) {
        Usuarios u = usuariosServicios.agregar(usu);
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }

    @DeleteMapping("/{idUsuarios}")
    public ResponseEntity<Boolean> EliminarUsuarioId(@PathVariable Integer idUsuarios) {
        boolean delet = usuariosServicios.eliminar(idUsuarios);
        if (delet) {
            return ResponseEntity.ok(delet);
        }
        return ResponseEntity.notFound().build();
    }

}
