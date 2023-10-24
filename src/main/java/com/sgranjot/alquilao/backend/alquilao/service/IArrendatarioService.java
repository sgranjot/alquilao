package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;
import com.sgranjot.alquilao.backend.alquilao.response.ArrendatarioResponseRest;

public interface IArrendatarioService {
    
    public ResponseEntity<ArrendatarioResponseRest> buscarArrendatarios ();
    public ResponseEntity<ArrendatarioResponseRest> buscarPorId (Long id);
    public ResponseEntity<ArrendatarioResponseRest> crear (Arrendatario arrendatario);
    public ResponseEntity<ArrendatarioResponseRest> actualizar (Arrendatario arrendatario, Long id);
    public ResponseEntity<ArrendatarioResponseRest> eliminar (Long id);

}
