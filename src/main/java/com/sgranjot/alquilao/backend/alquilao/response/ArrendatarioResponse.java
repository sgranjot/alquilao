package com.sgranjot.alquilao.backend.alquilao.response;

import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;

public class ArrendatarioResponse {
    
    private List<Arrendatario> arrendatarios;

    
    public List<Arrendatario> getArrendatarios() {
        return arrendatarios;
    }

    public void setArrendatarios(List<Arrendatario> arrendatarios) {
        this.arrendatarios = arrendatarios;
    }
    

}
