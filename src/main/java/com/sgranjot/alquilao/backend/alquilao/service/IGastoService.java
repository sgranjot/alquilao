package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Gasto;
import com.sgranjot.alquilao.backend.alquilao.response.GastoResponseRest;

public interface IGastoService {
    
    public ResponseEntity<GastoResponseRest> buscarGastos ();
    public ResponseEntity<GastoResponseRest> buscarPorId (Long id);
    public ResponseEntity<GastoResponseRest> crear (Gasto gasto);
    public ResponseEntity<GastoResponseRest> actualizar (Gasto gasto, Long id);
    public ResponseEntity<GastoResponseRest> eliminar (Long id);

}
