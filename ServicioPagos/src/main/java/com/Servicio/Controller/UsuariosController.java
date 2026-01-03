package com.Servicio.Controller;

import com.Servicio.Entity.Usuarios;
import com.Servicio.Security.UsuarioPrincipal;
import com.Servicio.Service.UsuariosServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UsuariosController {
    @Autowired
    private UsuariosServicios usuariosServicios;

    @GetMapping("/verusuarios")
    public String verUsuario(Model model ,Authentication authentication) {
        UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();
        if(principal.getRol().equalsIgnoreCase("ADMIN")) {
            model.addAttribute("usuarios", usuariosServicios.listar());
        } else {
            // EMPLEADO solo puede ver su propio registro
            model.addAttribute("usuarios", java.util.List.of(usuariosServicios.usuarioLogin(principal.getUsername())));
        }
        return "verusuarios";
    }

    @GetMapping("/agregarUsuario")
    public String mostrarFormularioUsuario(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "agregarUsuario";
    }


    @PostMapping("/agregarUsuario")
    public String agregarUsuarioFormulario(Usuarios usu) {
        usuariosServicios.agregar(usu);
        return "redirect:/verusuarios";
    }


    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        usuariosServicios.eliminar(id);
        return "redirect:/verusuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarEditarUsuario(@PathVariable("id") Integer id, Model model) {
        Usuarios usuario = usuariosServicios.buscar(id);
        model.addAttribute("usuario", usuario);
        return "agregarUsuario";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuarios usuario) {
        usuariosServicios.agregar(usuario);
        return "redirect:/verusuarios";
    }

}
