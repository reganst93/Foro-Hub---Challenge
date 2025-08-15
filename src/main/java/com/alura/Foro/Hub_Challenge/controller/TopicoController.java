package com.alura.Foro.Hub_Challenge.controller;

import com.alura.Foro.Hub_Challenge.domain.curso.Categoria;
import com.alura.Foro.Hub_Challenge.domain.curso.Curso;
import com.alura.Foro.Hub_Challenge.domain.curso.CursoRepository;
import com.alura.Foro.Hub_Challenge.domain.topico.Topico;
import com.alura.Foro.Hub_Challenge.domain.topico.TopicoRepository;
import com.alura.Foro.Hub_Challenge.domain.topico.datosTopico;
import com.alura.Foro.Hub_Challenge.domain.usuario.Usuario;
import com.alura.Foro.Hub_Challenge.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tópicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    private Topico buscarTopico(Long id){
        return topicoRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"El id que busca no existe"));

    }
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
                "VISIBLE",
                autor,
                curso,
                new ArrayList<>());
        topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tópico creado con exito");
    }

    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos(){
        List<Topico> topicos = topicoRepository.findByStatus("VISIBLE");
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("ultimos")
    public ResponseEntity<List<Topico>> listarTopicosOrdenados() {
        Page<Topico> topicoPage = topicoRepository.findByStatus("VISIBLE", PageRequest.of(0, 10, Sort.by("fechaCreacion").ascending()));
        return ResponseEntity.ok(topicoPage.getContent());
    }

    @GetMapping("/buscarPorCategoria")
    public ResponseEntity<List<Topico>> buscarPorCategoria(@RequestParam Categoria categoria){
        List<Topico> topicos = topicoRepository.findByCursoCategoriaAndStatus(categoria,"VISIBLE");
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalleTopico(@PathVariable Long id){
        try {
            Topico topico = buscarTopico(id);
            if(!"VISIBLE".equals(topico.getStatus())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("El tópico no está disponible");
            }
            return ResponseEntity.ok(topico);
        } catch (ResponseStatusException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getReason());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid datosTopico datos){
        Topico topico;
        try {
            topico = buscarTopico(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
        }

        String nuevoTitulo = datos.titulo() != null ? datos.titulo() : topico.getTitulo();
        String nuevoMensaje = datos.mensaje() != null ? datos.mensaje() : topico.getMensaje();

        Optional<Topico> duplicado = topicoRepository.findByTituloAndMensaje(nuevoTitulo, nuevoMensaje);
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un tópico con ese título y mensaje");
        }

        if (datos.titulo() != null && !datos.titulo().isBlank()) {
            topico.setTitulo(datos.titulo());
        }

        if (datos.mensaje() != null && !datos.mensaje().isBlank()) {
            topico.setMensaje(datos.mensaje());
        }

        if (datos.curso() != null) {
            Curso curso = cursoRepository.findById(datos.curso()).orElse(null);
            if (curso == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Curso no encontrado");
            }
            topico.setCurso(curso);
        }

        topicoRepository.save(topico);
        return ResponseEntity.ok("Se actualizo el tópico con exito");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarTopico (@PathVariable Long id){
        Topico topico;
        try {
            topico = buscarTopico(id);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
        }
        topico.setStatus("ELIMINADO");
        topicoRepository.save(topico);
        return ResponseEntity.ok("El tópico ha sido eliminado correctamente");
    }
}