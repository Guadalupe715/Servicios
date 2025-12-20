package com.Servicio.Controller;

import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;
import com.Servicio.Entity.Pagos;
import com.Servicio.Repository.PagosRepocitorio;
import com.Servicio.Service.PagosServicios;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
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
    public void generarPdf(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        Pagos pago = pagosRepocitorio.findById(id).orElse(null);
        if("PENDIENTE".equals(pago.getEstado())){
            pago.setEstado("COMPLETADO");
            pagosRepocitorio.save(pago);
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pago_" + id + ".pdf");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.setLeading(20f);
            content.newLineAtOffset(50, 700);

            content.showText("==== Detalle de Pago ====");
            content.newLine();
            content.showText("Empresa: "+ pago.getCuentaSuministro().getNombreEmpresa());
            content.newLine();
            content.showText("Direccion: "+pago.getCuentaSuministro().getDireccion());
            content.newLine();
            content.showText("Usuario: " + pago.getUsuarios().getNombre());
            content.newLine();
            content.showText("Servicio: " + pago.getServicios().getNombre());
            content.newLine();
            content.showText("Cuenta Suministro: " + pago.getCuentaSuministro().getCodigoSuministro());
            content.newLine();
            content.showText("Método de Pago: " + pago.getMetodoPago().getNombre());
            content.newLine();
            content.showText("Código Operación: " + pago.getCodigoOperacion());
            content.newLine();
            content.showText("Número Comprobante: " + pago.getNumeroComprobante());
            content.newLine();
            content.showText("Fecha de Pago: " + pago.getFechaPago());
            content.newLine();
            content.showText("Estado: " + pago.getEstado());
            content.newLine();
            content.showText("Cliente: "+pago.getCuentaSuministro().getNombreCliente());
            content.newLine();
            content.showText("Cliente: "+pago.getCuentaSuministro().getTelefono());
            content.newLine();
            content.showText("Ofrecemos: "+pago.getCuentaSuministro().getServiciosOfrecidos());
            content.newLine();
            content.showText("Grecias: "+pago.getCuentaSuministro().getPublicidad());
            content.endText();
            content.close();

            document.save(response.getOutputStream());
        }
    }

}
