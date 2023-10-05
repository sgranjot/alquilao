package com.sgranjot.alquilao.backend.alquilao.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.sgranjot.alquilao.backend.alquilao.model.Contrato;

public interface IContratoDao extends CrudRepository <Contrato, Long> {
    
}
