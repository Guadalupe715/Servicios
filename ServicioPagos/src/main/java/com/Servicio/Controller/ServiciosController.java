package com.Servicio.Controller;

import com.Servicio.DTOresponse.CuentaResponseDTO;
import com.Servicio.Entity.MetodoPago;
import com.Servicio.Entity.Servicios;
import com.Servicio.Service.CuentaServicios;
import com.Servicio.Service.EmpresaServicios;
import com.Servicio.Service.MetodoServicios;
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
    @Autowired
    private EmpresaServicios empresaServicios;
    @Autowired
    private MetodoServicios metodoServicios;
    @Autowired
    private CuentaServicios cuentaServicios;
    @GetMapping("/ver")
    public String verServicios(Model model) {
        List<Servicios> servicios = serviciosServicios.listar();
        List<MetodoPago> metodos = metodoServicios.listar();
        List<CuentaResponseDTO> listaCuentas = cuentaServicios.listar();

        model.addAttribute("servicios", servicios);
        model.addAttribute("metodosPago", metodos);
        model.addAttribute("listaCuentas", listaCuentas);

        return "verServicios";
    }

    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("servicio", new Servicios());
        model.addAttribute("empresas", empresaServicios.listar());
        return "agregarServicios";
    }

    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute Servicios servicio) {
        serviciosServicios.agregar(servicio);
        return "redirect:/servicios/ver";
    }
    @GetMapping("/empresa/{idEmpresa}")
    public String verServiciosEmpresa(@PathVariable Integer idEmpresa, Model model) {
        List<Servicios> servicios = serviciosServicios.listarEmpresa(idEmpresa);
        List<MetodoPago> metodos = metodoServicios.listar();

        model.addAttribute("servicios", servicios);
        model.addAttribute("empresaSeleccionada", idEmpresa);
        model.addAttribute("metodosPago", metodos);
        return "verServicios";
    }
}
