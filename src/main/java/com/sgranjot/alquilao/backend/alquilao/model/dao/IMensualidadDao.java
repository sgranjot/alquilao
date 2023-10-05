package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;

public interface IMensualidadDao extends CrudRepository <Mensualidad, Long> {
    
}
