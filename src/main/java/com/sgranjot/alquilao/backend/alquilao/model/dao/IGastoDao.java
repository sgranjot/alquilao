package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Gasto;

public interface IGastoDao extends CrudRepository <Gasto, Long>{
    
}
