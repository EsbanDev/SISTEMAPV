package com.sistemapv.controller;

import com.sistemapv.dto.CajaDTO;
import com.sistemapv.dto.CajaResponseDTO;
import com.sistemapv.dto.UbicarEnRackDTO;
import com.sistemapv.model.Usuario;
import com.sistemapv.service.CajaService;
import com.sistemapv.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cajas")
public class CajaController {

    @Autowired
    private CajaService cajaService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> crearCaja(@ModelAttribute CajaDTO dto) throws IOException {
        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();
        cajaService.crearCaja(usuarioActual, dto);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @PostMapping("/ubicar")
    public ResponseEntity<Void> ubicarEnRack(@ModelAttribute UbicarEnRackDTO dto) throws IOException {
        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();
        cajaService.ubicarEnRack(usuarioActual, dto);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @GetMapping
    public List<CajaResponseDTO> listar() {
        return cajaService.listarCajas();
    }

    @GetMapping("/rack/{codigoRack}")
    public List<CajaResponseDTO> listarCajasDelRack(@PathVariable String codigoRack) {
        return cajaService.listarCajasDelRack(codigoRack);
    }

    @GetMapping("/usuario/{id}")
    public List<CajaResponseDTO> buscarPorUsuario(@PathVariable Long id) {
        return cajaService.buscarPorUsuario(id);
    }


    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        cajaService.eliminarCaja(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/{codigoCaja}")
    public ResponseEntity<CajaResponseDTO> obtenerPorCodigoCaja(@PathVariable String codigoCaja) {
        return ResponseEntity.ok(cajaService.obtenerPorCodigoCaja(codigoCaja));
    }
}
