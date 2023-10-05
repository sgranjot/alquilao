package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;

public interface IPropiedadDao extends CrudRepository <Propiedad, Long> {
    
}
