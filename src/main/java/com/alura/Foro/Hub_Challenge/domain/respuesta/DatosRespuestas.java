package com.alura.Foro.Hub_Challenge.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuestas(
        String mensaje,
        String topico,
        LocalDateTime fechaCreacionR,
        String autorRespuesta,
        String solucion
) {
}
