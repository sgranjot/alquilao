package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Direccion;
import com.sgranjot.alquilao.backend.alquilao.response.DireccionResponseRest;

public interface IDireccionService {
    
    public ResponseEntity<DireccionResponseRest> buscarDirecciones ();
    public ResponseEntity<DireccionResponseRest> buscarPorId (Long id);
    public ResponseEntity<DireccionResponseRest> crear (Direccion direccion);
    public ResponseEntity<DireccionResponseRest> actualizar (Direccion direccion, Long id);
    public ResponseEntity<DireccionResponseRest> eliminar (Long id);

}
