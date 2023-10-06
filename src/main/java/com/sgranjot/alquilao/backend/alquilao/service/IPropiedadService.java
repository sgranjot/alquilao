package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;
import com.sgranjot.alquilao.backend.alquilao.response.PropiedadResponseRest;

public interface IPropiedadService {

    public ResponseEntity<PropiedadResponseRest> buscarPropiedades ();
    public ResponseEntity<PropiedadResponseRest> buscarPorId (Long id);
    public ResponseEntity<PropiedadResponseRest> crear (Propiedad propiedad);
    public ResponseEntity<PropiedadResponseRest> actualizar (Propiedad propiedad, Long id);
    public ResponseEntity<PropiedadResponseRest> eliminar (Long id);
    
}
