package com.sistemapv.service;

import com.sistemapv.dto.ProductoCreateDTO;
import com.sistemapv.dto.ProductoResponseDTO;
import com.sistemapv.model.Producto;
import com.sistemapv.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public void crearProducto(ProductoCreateDTO dto) {
        Producto producto = new Producto();
        producto.setSku(dto.getSku());
        producto.setNombreProd(dto.getNombreProd());
        producto.setStock(dto.getStock());

        productoRepository.save(producto);
    }

    public List<ProductoResponseDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::mapToDTO).toList();
    }

    public ProductoResponseDTO obtenerPorSku(int sku) {
        Producto producto = productoRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return mapToDTO(producto);
    }

    public ProductoResponseDTO mapToDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setSku(producto.getSku());
        dto.setNombreProd(producto.getNombreProd());
        dto.setStock(producto.getStock());

        return dto;
    }
}
