package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;

public interface IArrendatarioDao extends CrudRepository <Arrendatario, Long> {
    
}
