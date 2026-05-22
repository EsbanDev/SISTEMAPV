package com.sistemapv.service;

import com.sistemapv.dto.RackCreateDTO;
import com.sistemapv.dto.RackResponseDTO;
import com.sistemapv.model.Rack;
import com.sistemapv.repository.RackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RackService {

    @Autowired
    private RackRepository rackRepository;

    public void crearRack(RackCreateDTO dto) {

        if(rackRepository.findByCodigoRack(dto.getCodigoRack()).isPresent()){
            throw new RuntimeException("Ya existe un rack con código: " + dto.getCodigoRack());
        }

        Rack rack = new Rack();
        rack.setCodigoRack(dto.getCodigoRack());
        rack.setCapacidadMaxima(dto.getCapacidadMaxima());
        rack.setCapacidadActual(0);

        rackRepository.save(rack);
    }

    public List<RackResponseDTO> listarRacks() {
        List<Rack> racks = rackRepository.findAll();
        return racks.stream().map(this::mapToDTO).toList();
    }

    public RackResponseDTO obtenerPorCodigoRack(String codigoRack) {
        Rack rack = rackRepository.findByCodigoRack(codigoRack)
                .orElseThrow(() -> new RuntimeException("Rack no encontrado"));

        return mapToDTO(rack);
    }

    public RackResponseDTO mapToDTO(Rack rack) {
        RackResponseDTO dto = new RackResponseDTO();
        dto.setId(rack.getId());
        dto.setCodigoRack(rack.getCodigoRack());
        dto.setCapacidadMaxima(rack.getCapacidadMaxima());
        dto.setCapacidadActual(rack.getCapacidadActual());

        return dto;
    }
}
