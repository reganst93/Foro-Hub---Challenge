package com.alura.Foro.Hub_Challenge.controller;

import com.alura.Foro.Hub_Challenge.domain.curso.Curso;
import com.alura.Foro.Hub_Challenge.domain.curso.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity crearCurso (@RequestBody Curso curso){
        cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body("Curso creado con éxito");
    }
}
