package com.alura.Foro.Hub_Challenge.controller;

import com.alura.Foro.Hub_Challenge.domain.curso.Curso;
import com.alura.Foro.Hub_Challenge.domain.curso.CursoRepository;
import com.alura.Foro.Hub_Challenge.domain.topico.Topico;
import com.alura.Foro.Hub_Challenge.domain.topico.TopicoRepository;
import com.alura.Foro.Hub_Challenge.domain.topico.datosTopico;
import com.alura.Foro.Hub_Challenge.domain.usuario.Usuario;
import com.alura.Foro.Hub_Challenge.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/tópicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @PostMapping
    public ResponseEntity registrarTopico (@RequestBody @Valid datosTopico datos){
        if(topicoRepository.findByTituloAndMensaje(datos.titulo(),datos.mensaje()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error: ya existe un topico con ese titulo y ese mensaje");
        }

        Usuario autor = usuarioRepository.findById(datos.autor()).orElse(null);
        if(autor ==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error el autor no a sido encontrado");
        }

        Curso curso = cursoRepository.findById(datos.curso()).orElse(null);
        if(curso==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: curso no encontrado");
        }
        Topico topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                LocalDateTime.now(),
                "NO_RESPONDIDO",
                autor,
                curso,
                new ArrayList<>());
        topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tópico creado con exito");
    }
}
