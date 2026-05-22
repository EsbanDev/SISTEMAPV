package com.sistemapv.dto;

import com.sistemapv.model.Rol;
import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String rol; // luego se convierte a Enum

    public Rol getRolEnum(){
        try{
            return Rol.valueOf(rol.toUpperCase());
        }
        catch(Exception e){
            throw new IllegalArgumentException("Rol inválido");
        }
    }
}
