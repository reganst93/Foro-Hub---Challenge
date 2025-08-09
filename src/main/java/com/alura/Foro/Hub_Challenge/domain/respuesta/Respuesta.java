package com.alura.Foro.Hub_Challenge.domain.respuesta;

import com.alura.Foro.Hub_Challenge.domain.topico.Topico;
import com.alura.Foro.Hub_Challenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name ="respuesta")
@Entity(name = "Respuesta")
public class Respuesta {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String mensaje;
    @ManyToOne
    @JoinColumn(name = "topico_id")
    @NotNull
    private Topico topico;
    @NotBlank
    private LocalDateTime fechaCreacionR;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    @NotNull
    private Usuario autor;

    private Boolean solucion;
}
