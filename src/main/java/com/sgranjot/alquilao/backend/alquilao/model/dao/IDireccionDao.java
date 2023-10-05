package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Direccion;

public interface IDireccionDao extends CrudRepository <Direccion, Long> {
    
}
