package com.sistemapv.controller;

import com.sistemapv.dto.RackCreateDTO;
import com.sistemapv.dto.RackResponseDTO;
import com.sistemapv.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racks")
public class RackController {

    @Autowired
    private RackService rackService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Void> crearRack(@RequestBody RackCreateDTO dto) {
        rackService.crearRack(dto);
        return ResponseEntity.status(201).build(); //
    }

    @GetMapping
    public List<RackResponseDTO> listar() {
        return rackService.listarRacks();
    }

    @GetMapping("/{codigoRack}")
    public ResponseEntity<RackResponseDTO> obtenerPorCodigoRack(@PathVariable String codigoRack) {
        return ResponseEntity.ok(rackService.obtenerPorCodigoRack(codigoRack));
    }
}
