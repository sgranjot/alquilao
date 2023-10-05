package com.sgranjot.alquilao.backend.alquilao.response;

import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Usuario;

public class UsuarioResponse {
    
    private List<Usuario> usuarios;

    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    
    
}
