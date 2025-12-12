package com.Servicio.Controller;

import com.Servicio.Entity.MetodoPago;
import com.Servicio.Service.MetodoServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/met")
public class MetodoController {
    @Autowired
    private MetodoServicios metodoServicios;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> ListarMetodo() {
        List<MetodoPago> metodoPagos = metodoServicios.listar();
        if (metodoPagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(metodoPagos);
    }

    @GetMapping("/{idMetodo}")
    public ResponseEntity<MetodoPago> BuscarMetodoId(@PathVariable Integer idMetodo) {
        MetodoPago meto = metodoServicios.buscar(idMetodo);
        if (meto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(meto);
    }

    @PostMapping("/agregar")
    public ResponseEntity<MetodoPago> AgregarMetodo(@RequestBody MetodoPago meto) {
        MetodoPago m = metodoServicios.agregar(meto);
        if (m == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(m);
    }

    @DeleteMapping("/{idMetodo}")
    public ResponseEntity<Boolean> EliminarMetodoId(@PathVariable Integer idMetodo) {
        boolean delet = metodoServicios.eliminar(idMetodo);
        if (delet) {
            return ResponseEntity.ok(delet);
        }
        return ResponseEntity.notFound().build();
    }

}
