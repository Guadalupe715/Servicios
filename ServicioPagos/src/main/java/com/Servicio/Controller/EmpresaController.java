package com.Servicio.Controller;

import com.Servicio.Entity.Empresa;
import com.Servicio.Entity.Servicios;
import com.Servicio.Service.EmpresaServicios;
import com.Servicio.Service.ServiciosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaServicios empresaServicios;

    @GetMapping("/ver")
    public String verEmpresa(Model model){
        List<Empresa> empresa = empresaServicios.listar();
        model.addAttribute("empresa",empresa);
        return "verEmpresa";
    }
    @GetMapping("/agregar")
    public String mostrarEmpresa(Model model) {
        model.addAttribute("empresas",new Empresa());
        return "agregarEmpresa";
    }
    @PostMapping("/guardar")
    public String guardarEmpresa(Empresa empresa){
        empresaServicios.agregar(empresa);
        return "redirect:/empresa/ver";
    }
}

