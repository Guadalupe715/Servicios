package com.Servicio.Controller;

import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;
import com.Servicio.Service.PagosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
public class PagosController {
    @Autowired
    private PagosServicios pagosServicios;

    @PostMapping("/generar")
    public ResponseEntity<PagosResponseDTO> GenerarPagos(@RequestBody PagosRequestDTO request){
        PagosResponseDTO pagos = pagosServicios.crearPago(request);
        if(pagos == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagos);
    }
}
