package com.Servicio.Controller;

import com.Servicio.DTOresponse.CuentaResponseDTO;
import com.Servicio.DTOresponse.Request.CuentaRequestDTO;
import com.Servicio.Service.CuentaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaServicios cuentaServicios;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCuenta(@ModelAttribute CuentaRequestDTO request) {
        CuentaResponseDTO cuenta = cuentaServicios.crear(request);
        return ResponseEntity.ok(cuenta);
    }
}
