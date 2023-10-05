package com.sgranjot.alquilao.backend.alquilao.response;

import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Gasto;

public class GastoResponse {
    
    private List<Gasto> gastos;

    
    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }


}
