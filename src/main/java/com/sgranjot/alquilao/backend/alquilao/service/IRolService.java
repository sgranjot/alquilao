package com.sgranjot.alquilao.backend.alquilao.service;

import org.springframework.http.ResponseEntity;

import com.sgranjot.alquilao.backend.alquilao.model.Rol;
import com.sgranjot.alquilao.backend.alquilao.response.RolResponseRest;

public interface IRolService {
    
    public ResponseEntity<RolResponseRest> buscarRoles ();
    public ResponseEntity<RolResponseRest> buscarPorId(Long id);
    public ResponseEntity<RolResponseRest> crear (Rol rol);
    public ResponseEntity<RolResponseRest> actualizar (Rol rol, Long id);
    public ResponseEntity<RolResponseRest> eliminar (Long id);

}
