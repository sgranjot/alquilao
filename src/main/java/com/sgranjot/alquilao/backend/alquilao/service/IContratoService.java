package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Contrato;
import com.sgranjot.alquilao.backend.alquilao.response.ContratoResponseRest;

public interface IContratoService {
    
    public ResponseEntity<ContratoResponseRest> buscarContratos ();
    public ResponseEntity<ContratoResponseRest> buscarPorId (Long id);
    public ResponseEntity<ContratoResponseRest> crear (Contrato contrato);
    public ResponseEntity<ContratoResponseRest> actualizar (Contrato contrato, Long id);
    public ResponseEntity<ContratoResponseRest> eliminar (Long id);

}
