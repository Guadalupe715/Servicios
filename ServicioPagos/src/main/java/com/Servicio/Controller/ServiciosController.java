package com.Servicio.Controller;

import com.Servicio.Entity.Servicios;
import com.Servicio.Service.ServiciosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/service")
public class ServiciosController {
    @Autowired
    private ServiciosServicios serviciosServicios;

    @GetMapping
    public ResponseEntity<List<Servicios>> ListarServicios() {
        List<Servicios> servicios = serviciosServicios.listar();
        if (servicios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{idServicios}")
    public ResponseEntity<Servicios> BuscarServiciosId(@PathVariable Integer idServicios) {
        Servicios serv = serviciosServicios.buscar(idServicios);
        if (serv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serv);
    }

    @PostMapping
    public ResponseEntity<Servicios> AgregarServicios(@RequestBody Servicios serv) {
        Servicios s = serviciosServicios.agregar(serv);
        if (s == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/{idServicios}")
    public ResponseEntity<Boolean> EliminarServiciosId(@PathVariable Integer idServicios) {
        boolean delet = serviciosServicios.eliminar(idServicios);
        if (delet) {
            return ResponseEntity.ok(delet);
        }
        return ResponseEntity.notFound().build();
    }
}
