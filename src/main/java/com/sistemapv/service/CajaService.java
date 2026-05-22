package com.sistemapv.service;

import com.sistemapv.exception.RecursoNoEncontradoException;
import com.sistemapv.dto.CajaDTO;
import com.sistemapv.dto.CajaResponseDTO;
import com.sistemapv.dto.UbicarEnRackDTO;
import com.sistemapv.model.*;
import com.sistemapv.repository.CajaRepository;
import com.sistemapv.repository.MovimientoRepository;
import com.sistemapv.repository.ProductoRepository;
import com.sistemapv.repository.RackRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CajaService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private RackRepository rackRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MovimientoRepository movimientoRepository;

    public void crearCaja(Usuario usuario, CajaDTO dto) {
        Caja caja = new Caja();
        Movimiento movimiento = new Movimiento();

        Producto producto =
                productoRepository.findBySku(dto.getSku())
                        .orElseThrow(() ->
                                new RecursoNoEncontradoException("Producto no encontrado"));

        producto.setStock(
                producto.getStock()
                        + dto.getCantidadProductos()
        );

        productoRepository.save(producto);

        caja.setCodigoCaja(dto.getCodigoCaja());
        caja.setCantidadProductos(dto.getCantidadProductos());
        caja.setFechaIngreso(LocalDateTime.now());
        caja.setProducto(producto);
        caja.setUsuario(usuario);

        movimiento.setTipoMov(TipoMov.INGRESO);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCaja(caja);
        movimiento.setUsuario(usuario);

        cajaRepository.save(caja);
        movimientoRepository.save(movimiento);
    }

    public void ubicarEnRack(Usuario usuario, UbicarEnRackDTO dto) {
        Caja caja = cajaRepository.findByCodigoCaja(dto.getCodigoCaja())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Caja no encontrada"));

        Rack rack = rackRepository.findByCodigoRack(dto.getCodigoRack())
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Rack no encontrado"));

        if(rack.getCapacidadActual() >= rack.getCapacidadMaxima()){
            throw new RuntimeException("Rack lleno");
        }

        rack.setCapacidadActual(
                rack.getCapacidadActual()+1
        );

        rackRepository.save(rack);

        caja.setRack(rack);

        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMov(TipoMov.TRASLADO);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setCaja(caja);
        movimiento.setRack(rack);
        movimiento.setUsuario(usuario);

        movimientoRepository.save(movimiento);
        cajaRepository.save(caja);
    }

    public List<CajaResponseDTO> listarCajas() {
        List<Caja> cajas = cajaRepository.findAll();
        return cajas.stream().map(this::mapToDTO).toList();
    }

    public List<CajaResponseDTO> listarCajasDelRack(String codigoRack) {
        List<Caja> cajas = cajaRepository.findByRack_CodigoRack(codigoRack);
        return cajas.stream().map(this::mapToDTO).toList();
    }

    public List<CajaResponseDTO> buscarPorUsuario(Long usuarioId) {
        List<Caja> cajas = cajaRepository.findByUsuarioId(usuarioId);
        return cajas.stream().map(this::mapToDTO).toList();
    }

    public CajaResponseDTO obtenerPorId(Long cajaId) {
        Caja caja = cajaRepository.findById(cajaId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Caja no encontrada"));

        return mapToDTO(caja);
    }

    public CajaResponseDTO obtenerPorCodigoCaja(String codigoCaja) {
        Caja caja = cajaRepository.findByCodigoCaja(codigoCaja)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Caja no encontrada"));

        return mapToDTO(caja);
    }

    public void eliminarCaja(Long cajaId) {
        Caja caja = cajaRepository.findById(cajaId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Caja no encontrada"));

        Usuario actual = usuarioService.obtenerUsuarioActual();

        // ADMIN puede eliminar
        if (actual.getRol().name().equals("ADMINISTRADOR")) {
            cajaRepository.delete(caja);
            return;
        }

        throw new AccessDeniedException("No tienes permiso para eliminar esta caja");
    }

    private CajaResponseDTO mapToDTO(Caja caja) {
        CajaResponseDTO dto = new CajaResponseDTO();
        dto.setId(caja.getId());
        dto.setCodigoCaja(caja.getCodigoCaja());
        dto.setCantidadProductos(caja.getCantidadProductos());
        dto.setFechaIngreso(caja.getFechaIngreso());

        if (caja.getUsuario() != null) {
            dto.setUsuarioId(caja.getUsuario().getId());
            dto.setUsuarioUsername(caja.getUsuario().getUsername());
        }

        if (caja.getRack() != null) {
            dto.setRackId(caja.getRack().getId());
            dto.setCodigoRack(caja.getRack().getCodigoRack());
        }

        if (caja.getProducto() != null) {
            dto.setProductoId(caja.getProducto().getId());
            dto.setSku(caja.getProducto().getSku());
            dto.setNombreProd(caja.getProducto().getNombreProd());
        }

        return dto;
    }
}
