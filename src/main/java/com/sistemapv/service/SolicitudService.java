package com.sistemapv.service;

import com.sistemapv.exception.RecursoNoEncontradoException;
import com.sistemapv.dto.SolicitudCreateDTO;
import com.sistemapv.dto.SolicitudResponseDTO;
import com.sistemapv.model.*;
import com.sistemapv.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.sistemapv.model.Estado.*;

@Service
public class SolicitudService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void crearSolicitud(Usuario usuario , SolicitudCreateDTO dto) {
        // Verifica si ya tiene un perfil

        Solicitud solicitud = new Solicitud();
        solicitud.setCantidad(dto.getCantidad());
        solicitud.setFecha(LocalDateTime.now());
        solicitud.setEstado(PENDIENTE);
        solicitud.setUsuario(usuario);
        solicitud.setProducto(productoRepository.findBySku(dto.getSku())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado")));

        solicitudRepository.save(solicitud);
    }

    public void concluirSolicitud(Long solicitudId , String codigoCaja) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Solicitud no encontrada"));

        solicitud.setEstado(CONCLUIDA);

        Movimiento movimiento = new Movimiento();

        movimiento.setTipoMov(TipoMov.SALIDA);
        movimiento.setFecha(LocalDateTime.now());

        movimiento.setUsuario(solicitud.getUsuario());

        Caja caja = cajaRepository.findByCodigoCaja(codigoCaja).orElseThrow(() ->
                new RecursoNoEncontradoException("Solicitud no encontrada"));;

        movimiento.setCaja(caja);

        movimiento.setRack(caja.getRack());

        movimientoRepository.save(movimiento);

        solicitudRepository.save(solicitud);
    }

    public void cancelarSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Solicitud no encontrada"));

        solicitud.setEstado(CANCELADA);

        solicitudRepository.save(solicitud);
    }

    public List<SolicitudResponseDTO> listarSolicitudes() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        return solicitudes.stream().map(this::mapToDTO).toList();
    }

    public List<SolicitudResponseDTO> listarSolicitudesDeProducto(int sku) {
        List<Solicitud> solicitudes = solicitudRepository.findByProducto_Sku(sku);
        return solicitudes.stream().map(this::mapToDTO).toList();
    }

    public SolicitudResponseDTO obtenerSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud no encontrada"));

        return mapToDTO(solicitud);
    }

    private SolicitudResponseDTO mapToDTO(Solicitud solicitud) {
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(solicitud.getId());
        dto.setCantidad(solicitud.getCantidad());
        dto.setFecha(solicitud.getFecha());
        dto.setEstado(solicitud.getEstado());

        if (solicitud.getUsuario() != null) {
            dto.setUsuarioId(solicitud.getUsuario().getId());
            dto.setUsuarioUsername(solicitud.getUsuario().getUsername());
        }

        if (solicitud.getProducto() != null) {
            dto.setProductoId(solicitud.getProducto().getId());
            dto.setSku(solicitud.getProducto().getSku());
            dto.setNombreProd(solicitud.getProducto().getNombreProd());
        }

        return dto;
    }
}