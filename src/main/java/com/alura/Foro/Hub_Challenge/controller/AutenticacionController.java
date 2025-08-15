package com.alura.Foro.Hub_Challenge.controller;

import com.alura.Foro.Hub_Challenge.domain.usuario.DatosAutentificacion;
import com.alura.Foro.Hub_Challenge.domain.usuario.Usuario;
import com.alura.Foro.Hub_Challenge.infra.security.DatosTokenJWT;
import com.alura.Foro.Hub_Challenge.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login (@RequestBody @Valid DatosAutentificacion datos){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(datos.email(), datos.contrasena())
            );

            Usuario usuario = (Usuario) auth.getPrincipal();

            String tokenJWT = tokenService.generarToken(usuario);

            return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }
}

