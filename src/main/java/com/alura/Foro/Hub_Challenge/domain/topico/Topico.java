package com.alura.Foro.Hub_Challenge.domain.topico;

import com.alura.Foro.Hub_Challenge.domain.curso.Curso;
import com.alura.Foro.Hub_Challenge.domain.respuesta.DatosRespuestas;
import com.alura.Foro.Hub_Challenge.domain.respuesta.Respuesta;
import com.alura.Foro.Hub_Challenge.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "topico", uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "mensaje"}))
@Entity(name = "Topico")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;
    @NotBlank
    private String mensaje;
    @NotBlank
    private LocalDateTime fechaCreacion;
    @NotBlank
    private String status;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    @NotNull
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    @NotNull
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;;
}
