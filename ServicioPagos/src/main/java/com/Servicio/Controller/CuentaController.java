package com.Servicio.Controller;

import com.Servicio.DTOresponse.CuentaResponseDTO;
import com.Servicio.DTOresponse.Request.CuentaRequestDTO;
import com.Servicio.Service.CuentaServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cuenta")
public class CuentaController {
    @Autowired
    private CuentaServicios cuentaServicios;

    @PostMapping("/registrar")
    public String registrarCuenta(@ModelAttribute CuentaRequestDTO request, Model model) {
        CuentaResponseDTO cuenta = cuentaServicios.crear(request);

        List<CuentaResponseDTO> listaCuentas = cuentaServicios.listar();

        model.addAttribute("cuentaConfirmacion", cuenta);
        model.addAttribute("listaCuentas",listaCuentas);

        return "verServicios";
    }

}
