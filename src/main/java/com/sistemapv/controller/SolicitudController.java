package com.sistemapv.controller;

import com.sistemapv.dto.SolicitudCreateDTO;
import com.sistemapv.dto.SolicitudResponseDTO;
import com.sistemapv.model.Usuario;
import com.sistemapv.service.SolicitudService;
import com.sistemapv.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/solicitud")
    public ResponseEntity<Void> crearSolicitud(@RequestBody SolicitudCreateDTO dto) {
        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();
        solicitudService.crearSolicitud(usuarioActual, dto);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/concluir/{solicitudId}/{codigoCaja}")
    public ResponseEntity<Void> concluirSolicitud(@PathVariable Long solicitudId, @PathVariable String codigoCaja) {
        solicitudService.concluirSolicitud(solicitudId, codigoCaja);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/cancelar/{solicitudId}")
    public ResponseEntity<Void> cancelarSolicitud(@PathVariable Long solicitudId) {
        solicitudService.cancelarSolicitud(solicitudId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<SolicitudResponseDTO> listar() {
        return solicitudService.listarSolicitudes();
    }

    @GetMapping("/{solicitudId}")
    public ResponseEntity<SolicitudResponseDTO> obtenerSolicitud(@PathVariable Long solicitudId) {
        return ResponseEntity.ok(solicitudService.obtenerSolicitud(solicitudId));
    }
}