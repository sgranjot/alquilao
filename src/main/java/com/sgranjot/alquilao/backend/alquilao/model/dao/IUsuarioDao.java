package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Usuario;

public interface IUsuarioDao extends CrudRepository <Usuario, Long> {

    public Usuario findByNombreUsuario(String nombreUsuario);
    
}
