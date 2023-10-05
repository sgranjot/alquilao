package com.sgranjot.alquilao.backend.alquilao.response;

import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;

public class MensualidadResponse {
    
    private List<Mensualidad> mensualidades;

    
    public List<Mensualidad> getMensualidades() {
        return mensualidades;
    }

    public void setMensualidades(List<Mensualidad> mensualidades) {
        this.mensualidades = mensualidades;
    }
    
    

}
