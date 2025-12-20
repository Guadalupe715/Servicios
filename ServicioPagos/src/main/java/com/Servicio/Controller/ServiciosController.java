package com.Servicio.Controller;

import com.Servicio.Entity.Servicios;
import com.Servicio.Service.ServiciosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    @Autowired
    private ServiciosServicios serviciosServicios;


    @GetMapping("/ver")
    public String verServicios(Model model) {
        List<Servicios> servicios = serviciosServicios.listar();
        model.addAttribute("servicios", servicios);
        return "verServicios";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicio", new Servicios());
        return "agregarServicios";
    }

    @PostMapping("/guardar")
    public String guardarServicio(Servicios servicio) {
        serviciosServicios.agregar(servicio);
        return "redirect:/servicios/ver";
    }
}
