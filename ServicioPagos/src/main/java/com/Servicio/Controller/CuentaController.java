package com.Servicio.Controller;

import com.Servicio.DTOresponse.CuentaResponseDTO;
import com.Servicio.DTOresponse.Request.CuentaRequestDTO;
import com.Servicio.Service.CuentaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cuenta")
public class CuentaController {
    @Autowired
    private CuentaServicios cuentaServicios;

    @PostMapping("/save")
    public ResponseEntity<CuentaResponseDTO> GuardarCuenta
            (@RequestBody CuentaRequestDTO request) {
        CuentaResponseDTO cuenta = cuentaServicios.crear(request);
        if (cuenta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }
    @GetMapping("/list")
    public ResponseEntity<List<CuentaResponseDTO>> ListarCuentas(){
        List<CuentaResponseDTO> cuenta = cuentaServicios.listar();
        if(cuenta.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuenta);
    }

}
