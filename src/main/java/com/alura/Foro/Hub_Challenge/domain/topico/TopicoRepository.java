package com.alura.Foro.Hub_Challenge.domain.topico;

import com.alura.Foro.Hub_Challenge.domain.curso.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);
    Page<Topico> findByStatus(String status, Pageable pageable);

    List<Topico> findByCursoCategoriaAndStatus(Categoria categoria, String status);
    List<Topico> findByStatus(String status);

}
