package com.Servicio.Service;

import com.Servicio.DTOresponse.ClienteResponseDTO;
import com.Servicio.DTOresponse.PagosResponseDTO;
import com.Servicio.DTOresponse.Request.PagosRequestDTO;
import com.Servicio.Entity.*;
import com.Servicio.Repository.*;
import com.Servicio.Service.Impl.SVpagos;
import com.Servicio.Util.GeneradorUtil;
import com.itextpdf.text.pdf.draw.LineSeparator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

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
        pagos.setFechaPago(LocalDateTime.now());

        Pagos guardar = pagosRepocitorio.save(pagos);

        pagos.setNumeroComprobante(
                GeneradorUtil.generarNumeroComprobante(pagos.getIdPagos())
        );

        pagos = pagosRepocitorio.save(pagos);

        return new PagosResponseDTO(
                pagos.getIdPagos(),
                cuentaSuministro.getNombreEmpresa(),
                cuentaSuministro.getDireccion(),
                cuentaSuministro.getRuc(),
                usuarios.getNombre(),
                new ClienteResponseDTO(
                        cuentaSuministro.getNombreCliente(),
                        cuentaSuministro.getTelefono(),
                        cuentaSuministro.getCodigoSuministro()
                ),
                pagos.getCodigoOperacion(),
                pagos.getNumeroComprobante(),
                pagos.getFechaPago(),
                servicios.getNombre(),
                cuentaSuministro.getMonto(),
                metodoPago.getNombre(),
                cuentaSuministro.getServiciosOfrecidos(),
                cuentaSuministro.getPublicidad()
        );

    }

    public void generarPdf(Integer id, HttpServletResponse response) throws Exception {

        Pagos pago = pagosRepocitorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "attachment; filename=comprobante_pago_" + id + ".pdf");

        Document document = new Document(PageSize.A4, 50, 50, 40, 40);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // 🔹 FUENTES
        Font title = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);
        Font subtitle = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        Font normal = new Font(Font.FontFamily.HELVETICA, 11);

        /* =================== ENCABEZADO =================== */

        Paragraph titulo = new Paragraph("COMPROBANTE DE PAGO", title);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph(" "));

        /* =================== EMPRESA =================== */

        Paragraph empresa = new Paragraph(pago.getCuentaSuministro().getNombreEmpresa(), subtitle);
        empresa.setAlignment(Element.ALIGN_CENTER);
        document.add(empresa);

        Paragraph empresaInfo = new Paragraph(
                "RUC: " + pago.getCuentaSuministro().getRuc() + "\n" +
                        pago.getCuentaSuministro().getDireccion(),
                normal
        );
        empresaInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(empresaInfo);

        document.add(new Paragraph(" "));
        addLine(document);

        /* =================== CLIENTE =================== */

        document.add(new Paragraph("DATOS DEL CLIENTE", subtitle));
        document.add(new Paragraph("Nombre: " +
                pago.getCuentaSuministro().getNombreCliente(), normal));
        document.add(new Paragraph("Código Suministro: " +
                pago.getCuentaSuministro().getCodigoSuministro(), normal));
        document.add(new Paragraph("Telefono: " +
                pago.getCuentaSuministro().getTelefono(), normal));


        document.add(new Paragraph(" "));
        addLine(document);

        /* =================== DETALLE DEL PAGO =================== */

        document.add(new Paragraph("DETALLE DEL PAGO", subtitle));

        document.add(new Paragraph("Servicio: " +
                pago.getServicios().getNombre(), normal));

        document.add(new Paragraph("Método de Pago: " +
                pago.getMetodoPago().getNombre(), normal));

        document.add(new Paragraph("Monto Pagado: S/ " +
                pago.getCuentaSuministro().getMonto(), normal));

        document.add(new Paragraph("Fecha de Pago: " +
                pago.getFechaPago(), normal));

        document.add(new Paragraph("Código de Operación: " +
                pago.getCodigoOperacion(), normal));

        document.add(new Paragraph("N° Comprobante: " +
                pago.getNumeroComprobante(), normal));

        document.add(new Paragraph("" + pago.getCuentaSuministro().getServiciosOfrecidos(), normal));

        document.add(new Paragraph(" "));
        addLine(document);

        /* =================== PIE =================== */

        Paragraph pie = new Paragraph(
                "Este comprobante acredita el pago realizado.\nGracias por confiar en nosotros.",
                normal
        );
        pie.setAlignment(Element.ALIGN_CENTER);
        document.add(pie);

        document.close();
    }

    private void addLine(Document document) throws DocumentException {
        LineSeparator line = new LineSeparator();
        line.setLineWidth(0.8f);
        document.add(new Chunk(line));
    }


}
