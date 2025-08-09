package com.alura.Foro.Hub_Challenge.domain.topico;

import com.alura.Foro.Hub_Challenge.domain.respuesta.DatosRespuestas;

import java.time.LocalDateTime;

public record datosTopico(
        String titulo,
        String mensaje,
        Long autor,
        Long curso
        ) {


}
