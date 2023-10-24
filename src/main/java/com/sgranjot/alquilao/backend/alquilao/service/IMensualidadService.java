package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;
import com.sgranjot.alquilao.backend.alquilao.response.MensualidadResponseRest;

public interface IMensualidadService {

    public ResponseEntity<MensualidadResponseRest> buscarMensualidades ();
    public ResponseEntity<MensualidadResponseRest> buscarPorId (Long id);
    public ResponseEntity<MensualidadResponseRest> crear (Mensualidad mensualidad);
    public ResponseEntity<MensualidadResponseRest> actualizar (Mensualidad mensualidad, Long id);
    public ResponseEntity<MensualidadResponseRest> eliminar (Long id);

}
