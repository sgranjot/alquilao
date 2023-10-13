package com.sgranjot.alquilao.backend.alquilao.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;
import com.sgranjot.alquilao.backend.alquilao.model.Contrato;
import com.sgranjot.alquilao.backend.alquilao.model.Direccion;
import com.sgranjot.alquilao.backend.alquilao.model.Gasto;
import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;
import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;
import com.sgranjot.alquilao.backend.alquilao.model.Propiedad.Tipo;
import com.sgranjot.alquilao.backend.alquilao.model.Rol;
import com.sgranjot.alquilao.backend.alquilao.model.Usuario;

public class DataDummy {

    public static final Usuario USUARIO;
    
    public static final Arrendatario ARRENDATARIO, ARRENDATARIO2, ARRENDATARIO3;

    public static final Contrato CONTRATO, CONTRATO2, CONTRATO3;

    public static final Propiedad PROPIEDAD;

    public static final Direccion DIRECCION;

    public static final List<Contrato> CONTRATOS;

    public static final List<Rol> ROLES;
    
    public static final List<Propiedad> PROPIEDADES;

    public static final List<Arrendatario> ARRENDATARIOS;

    public static final List<Direccion> DIRECCIONES;

    public static final List<Mensualidad> MENSUALIDADES;

    public static final List<Gasto> GASTOS;


    static {
 
        ROLES = new ArrayList<>();
        
        PROPIEDADES = new ArrayList<>();

        DIRECCIONES = new ArrayList<>();

        MENSUALIDADES = new ArrayList<>();

        GASTOS = new ArrayList<>();
        
        CONTRATO2 = new Contrato(2L, LocalDate.of(2010, 01, 06), LocalDate.of(2015, 01, 06), 1400, 700, MENSUALIDADES, new Propiedad(), new Arrendatario(), new Usuario());

        CONTRATO3 = new Contrato(3L, LocalDate.of(2015, 05, 15), LocalDate.of(2020, 05, 15), 1000, 500, MENSUALIDADES, new Propiedad(), new Arrendatario(), new Usuario());
       
        CONTRATOS = new ArrayList<>(Arrays.asList(CONTRATO2, CONTRATO3));
        
        ARRENDATARIO2 = new Arrendatario(2L, "ArrendatarioTest2", "apellidos test2", "00000000B", "223456789", "arrendatario2@test.com", CONTRATOS, new Usuario());

        ARRENDATARIO3 = new Arrendatario(3L, "ArrendatarioTest3", "apellidos test3", "00000000C", "323456789", "arrendatario3@test.com", CONTRATOS, new Usuario());

        ARRENDATARIOS = new ArrayList<>(Arrays.asList(ARRENDATARIO2,ARRENDATARIO3));
          
        USUARIO = new Usuario(1L, "UsuarioTest", "passwordTest", true, ROLES, PROPIEDADES, ARRENDATARIOS, DIRECCIONES);

        ARRENDATARIO = new Arrendatario(1L, "ArrendatarioTest", "apellidos test", "00000000A", "123456789", "arrendatario@test.com", CONTRATOS, USUARIO);

        DIRECCION = new Direccion(1L, "CalleTest", "NúmeroTest", "PisoTest", "PuertaTest", "99999", "PoblaciónTest", "ProvinciaTest", "PaisTest", null, USUARIO);

        PROPIEDAD = new Propiedad(1L, false, Tipo.VIVIENDA, DIRECCION, GASTOS, CONTRATOS, USUARIO);
     
        CONTRATO = new Contrato(1L, LocalDate.of(2020, 10, 12), LocalDate.of(2025, 10, 12), 1200, 600, MENSUALIDADES, PROPIEDAD, ARRENDATARIO, USUARIO);

    }

}
