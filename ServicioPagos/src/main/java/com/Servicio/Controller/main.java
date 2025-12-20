package com.Servicio.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class main {


        @GetMapping("/")
        public String index() {
            return "index"; // main.html dentro de templates
        }
    }


