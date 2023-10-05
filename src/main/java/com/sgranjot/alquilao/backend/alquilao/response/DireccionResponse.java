package com.sgranjot.alquilao.backend.alquilao.response;

import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Direccion;

public class DireccionResponse {
    
    private List<Direccion> direcciones;


    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }
 

}
