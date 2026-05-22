package com.sistemapv.dto;

import com.sistemapv.model.Rol;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private Rol rol;
}
