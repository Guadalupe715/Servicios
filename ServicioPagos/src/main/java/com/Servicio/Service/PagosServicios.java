package com.Servicio.Service;

import com.Servicio.DTOresponse.ClienteResponseDTO;
import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;
import com.Servicio.Entity.*;
import com.Servicio.Repository.*;
import com.Servicio.Service.Impl.SVpagos;
import com.Servicio.Util.GeneradorUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@Service
public class PagosServicios implements SVpagos {
    @Autowired
    private UsuariosRepocitorio usuariosRepocitorio;
    @Autowired
    private CuentaSuministroRepositorio cuentaSuministroRepositorio;
    @Autowired
    private ServiciosRepocitorio serviciosRepocitorio;
    @Autowired
    private MetodoPagoRepositorio metodoPagoRepositorio;
    @Autowired
    private PagosRepocitorio pagosRepocitorio;

    @Override
    public PagosResponseDTO crearPago(PagosRequestDTO request) {
        Usuarios usuarios = usuariosRepocitorio.findById(request.getIdUsuarios()).orElseThrow(() -> new RuntimeException("Usuario no Encontrado"));
        Servicios servicios = serviciosRepocitorio.findById(request.getIdServicios()).orElseThrow(() -> new RuntimeException("Servicio no Encontrado"));
        CuentaSuministro cuentaSuministro = cuentaSuministroRepositorio.findById(request.getIdCuenta()).orElseThrow(() -> new RuntimeException("Cuenta no Encontrado"));
        MetodoPago metodoPago = metodoPagoRepositorio.findById(request.getIdMetodo()).orElseThrow(() -> new RuntimeException("Metodo no Encontrado"));

        Pagos pagos = new Pagos();
        pagos.setUsuarios(usuarios);
        pagos.setServicios(servicios);
        pagos.setMetodoPago(metodoPago);
        pagos.setCuentaSuministro(cuentaSuministro);
        pagos.setCodigoOperacion(GeneradorUtil.generarCodigoOperacion());
        pagos.setNumeroComprobante(GeneradorUtil.generaNumeroComprobante());
        pagos.setFechaPago(LocalDateTime.now());

        Pagos guardar = pagosRepocitorio.save(pagos);

        PagosResponseDTO comprobante = new PagosResponseDTO(
                guardar.getIdPagos(),
                cuentaSuministro.getNombreEmpresa(),
                cuentaSuministro.getDireccion(),
                cuentaSuministro.getRuc(),
                usuarios.getNombre(),
                        new ClienteResponseDTO(
                                cuentaSuministro.getNombreCliente(),
                                cuentaSuministro.getTelefono(),
                                cuentaSuministro.getCodigoSuministro()),
                        guardar.getCodigoOperacion(),
                        guardar.getNumeroComprobante(),
                        guardar.getFechaPago(),
                        servicios.getNombre(),
                        cuentaSuministro.getMonto(),
                        metodoPago.getNombre(),
                        cuentaSuministro.getServiciosOfrecidos(),
                        cuentaSuministro.getPublicidad());

        return comprobante;
    }

    public void generarPdf(Integer id, HttpServletResponse response) throws Exception {

        Pagos pago = pagosRepocitorio.findById(id).orElse(null);
        if (pago == null) return;

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pago_" + id + ".pdf");

        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            float y = 750;
            float marginX = 50;
            float col1 = 50;
            float col2 = 300;

            /* ---------------- LOGO ---------------- */
            var logoStream = getClass().getResourceAsStream("/static/img/logo.png");
            if (logoStream != null) {
                var image = org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
                        .createFromByteArray(document, logoStream.readAllBytes(), "logo");
                content.drawImage(image, 230, y - 60, 120, 50);
            }

            y -= 80;

            /* ---------------- EMPRESA ---------------- */
            float pageWidth = page.getMediaBox().getWidth();
            String empresa = pago.getCuentaSuministro().getNombreEmpresa();
            String direccion = pago.getCuentaSuministro().getDireccion();
            String ruc = pago.getCuentaSuministro().getRuc();

            /* -------- NOMBRE EMPRESA (CENTRADO) -------- */
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 14);
            content.setLeading(18f);

            float empresaWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(empresa) / 1000 * 14;
            float empresaX = (pageWidth - empresaWidth) / 2;
            content.newLineAtOffset(empresaX, y);
            content.showText(empresa);
            content.endText();
            y -= 30;

            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 13);

            float centro = pageWidth / 2;
            content.newLineAtOffset(centro - 140, y);
            content.showText(direccion);

            content.newLineAtOffset(180, 0);
            content.showText("RUC: " + ruc);
            content.endText();

            y -= 22;
            drawLine(content, y);
            y -= 18;

            /* -------- COLUMNA IZQUIERDA -------- */
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 11);
            content.setLeading(16f);
            content.newLineAtOffset(col1, y);
            content.showText("Entidad: " + pago.getServicios().getEmpresa().getNombre());
            content.newLine();
            content.showText("Código Suministro: " + pago.getCuentaSuministro().getCodigoSuministro());
            content.newLine();
            content.showText("Monto: S/. " + pago.getCuentaSuministro().getMonto());

            content.endText();

            /* -------- COLUMNA DERECHA -------- */
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 11);
            content.setLeading(16f);
            content.newLineAtOffset(col2, y);
            content.showText("Servicio: " + pago.getServicios().getNombre());
            content.newLine();
            content.showText("Método Pago: " + pago.getMetodoPago().getNombre());
            content.newLine();
            content.showText("Fecha: " + pago.getFechaPago());

            content.endText();


            y -= 70;
            drawLine(content, y);
            y -= 20;

            /* ---------------- COMPROBANTE ---------------- */
            content.beginText();
            content.setFont(PDType1Font.HELVETICA, 11);
            content.setLeading(16f);
            content.newLineAtOffset(marginX, y);
            content.showText("Código Operación: " + pago.getCodigoOperacion());
            content.newLine();
            content.showText("Número Comprobante: " + pago.getNumeroComprobante());
            content.endText();

            y -= 40;

            /* ---------------- TEXTO LARGO (AUTO SALTO) ---------------- */
            y = writeParagraph(document, content, page,
                    "Publicidad: " + pago.getCuentaSuministro().getPublicidad(), y);

            y = writeParagraph(document, content, page,
                    "Servicios Ofrecidos: " + pago.getCuentaSuministro().getServiciosOfrecidos(), y);

            content.close();
            document.save(response.getOutputStream());
        }

    }

    private void drawLine(PDPageContentStream content, float y) throws Exception {
        content.moveTo(50, y);
        content.lineTo(550, y);
        content.stroke();
    }

    private float writeParagraph(PDDocument doc, PDPageContentStream content,
                                 PDPage page, String text, float y) throws Exception {

        if (y < 100) {
            content.close();
            page = new PDPage();
            doc.addPage(page);
            content = new PDPageContentStream(doc, page);
            y = 750;
        }

        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 11);
        content.newLineAtOffset(50, y);

        for (String line : text.split("(?<=\\G.{80})")) {
            content.showText(line);
            content.newLine();
            y -= 15;
        }

        content.endText();
        return y - 10;
    }



}
