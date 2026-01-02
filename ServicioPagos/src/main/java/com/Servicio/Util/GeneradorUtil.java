package com.Servicio.Util;

import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorUtil {

    public static String generarCodigoOperacion() {
        String fecha = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = (int) (Math.random() * 9000) + 1000;

        return "OP-" + fecha + "-" + random;
    }

    public static String generarNumeroComprobante(Integer idPago) {
        return "C-" + String.format("%06d", idPago);
    }


}
