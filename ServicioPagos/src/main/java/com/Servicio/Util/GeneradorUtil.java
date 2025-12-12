package com.Servicio.Util;

import java.util.concurrent.atomic.AtomicInteger;

public class GeneradorUtil {

    private static final AtomicInteger contador = new AtomicInteger(1000);

    public static String generarCodigoOperacio() {
        String fecha = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int random = (int) (Math.random() * 9000) + 1000;

        return "OP-" + fecha + "-" + random;
    }

    public static String generaNumeroComprobante() {
        int numero = contador.incrementAndGet();
        return "C-"+ numero;
    }
}
