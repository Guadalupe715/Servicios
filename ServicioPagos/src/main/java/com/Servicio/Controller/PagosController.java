package com.Servicio.Controller;

import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;
import com.Servicio.Entity.Pagos;
import com.Servicio.Repository.PagosRepocitorio;
import com.Servicio.Service.PagosServicios;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/pagos")
public class PagosController {
    @Autowired
    private PagosServicios pagosServicios;
    @Autowired
    private PagosRepocitorio pagosRepocitorio;

    @PostMapping("/generar")
    public ResponseEntity<PagosResponseDTO> GenerarPagos(@RequestBody PagosRequestDTO request) {
        PagosResponseDTO pagos = pagosServicios.crearPago(request);
        if (pagos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/ver")
    public String pagos(Model model) {
        List<Pagos> pagos = pagosRepocitorio.findAll();
        model.addAttribute("pagos", pagos);
        return "pagos";
    }
    @GetMapping("/pdf/{id}")
    public void descargarPdf(@PathVariable Integer id,
                             HttpServletResponse response) throws Exception {
        pagosServicios.generarPdf(id, response);
    }
}
