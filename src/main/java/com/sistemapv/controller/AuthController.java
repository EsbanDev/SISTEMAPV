package com.sistemapv.controller;

import com.sistemapv.dto.LoginDTO;
import com.sistemapv.dto.RegisterDTO;
import com.sistemapv.service.UsuarioService;
import com.sistemapv.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;


    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );

            String token = jwtUtil.generarToken(dto.getUsername());

            return ResponseEntity.ok(
                    Map.of("token", token)
            );

        } catch (BadCredentialsException e) {

            return ResponseEntity
                    .status(401)
                    .body("Usuario o contraseña incorrectos.");

        } catch (Exception e) {

            return ResponseEntity
                    .status(500)
                    .body("Error al iniciar sesión.");

        }
    }


    // REGISTRO
    @PostMapping("/registro")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO dto) {
        try {

            usuarioService.registrarUsuario(dto);

            return ResponseEntity
                    .status(201)
                    .body("Usuario registrado exitosamente.");

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
