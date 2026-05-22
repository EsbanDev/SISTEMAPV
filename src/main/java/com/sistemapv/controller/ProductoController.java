package com.sistemapv.controller;

import com.sistemapv.dto.ProductoCreateDTO;
import com.sistemapv.dto.ProductoResponseDTO;
import com.sistemapv.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<Void> crearProducto(@RequestBody ProductoCreateDTO dto) {
        productoService.crearProducto(dto);
        return ResponseEntity.status(201).build(); //
    }

    @GetMapping
    public List<ProductoResponseDTO> listar() {
        return productoService.listarProductos();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorSku(@PathVariable int sku) {
        return ResponseEntity.ok(productoService.obtenerPorSku(sku));
    }
}
