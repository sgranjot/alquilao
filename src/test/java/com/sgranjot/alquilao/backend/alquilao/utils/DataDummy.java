package com.sgranjot.alquilao.backend.alquilao.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;
import com.sgranjot.alquilao.backend.alquilao.model.Contrato;
import com.sgranjot.alquilao.backend.alquilao.model.Direccion;
import com.sgranjot.alquilao.backend.alquilao.model.Gasto;
import com.sgranjot.alquilao.backend.alquilao.model.Gasto.Tipo;
import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;
import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;
import com.sgranjot.alquilao.backend.alquilao.model.Rol;
import com.sgranjot.alquilao.backend.alquilao.model.Usuario;

public class DataDummy {

    public static final Usuario USUARIO;
    
    public static final Arrendatario ARRENDATARIO, ARRENDATARIO2, ARRENDATARIO3;

    public static final Contrato CONTRATO, CONTRATO2, CONTRATO3;

    public static final Propiedad PROPIEDAD, PROPIEDAD2, PROPIEDAD3;

    public static final Direccion DIRECCION, DIRECCION2, DIRECCION3;

    public static final Gasto GASTO, GASTO2, GASTO3;

    public static final Mensualidad MENSUALIDAD, MENSUALIDAD2, MENSUALIDAD3;

    public static final List<Contrato> CONTRATOS;

    public static final List<Rol> ROLES;
    
    public static final List<Propiedad> PROPIEDADES;

    public static final List<Arrendatario> ARRENDATARIOS;

    public static final List<Direccion> DIRECCIONES;

    public static final List<Mensualidad> MENSUALIDADES;

    public static final List<Gasto> GASTOS;



    static {
 
        ROLES = new ArrayList<>();

        MENSUALIDAD2 = new Mensualidad(2L, 1000, LocalDate.of(2022, 05, 10), true, new Usuario(), new Contrato());

        MENSUALIDAD3 = new Mensualidad(3L, 900, LocalDate.of(2023, 05, 10), true, new Usuario(), new Contrato());

        MENSUALIDADES = new ArrayList<>(Arrays.asList(MENSUALIDAD2,MENSUALIDAD3));

        GASTO2 = new Gasto(2L, 1000, LocalDate.of(2023, 02, 01)  , "GastoTest2", Tipo.HIPOTECA, new Propiedad(), new Usuario());

        GASTO3 = new Gasto(3L, 300, LocalDate.of(2022, 12, 11)  , "GastoTest3", Tipo.IMPUESTO, new Propiedad(), new Usuario());

        GASTOS = new ArrayList<>(Arrays.asList(GASTO2, GASTO3));
        
        CONTRATO2 = new Contrato(2L, LocalDate.of(2010, 01, 06), LocalDate.of(2015, 01, 06), 1400, 700, MENSUALIDADES, new Propiedad(), new Arrendatario(), new Usuario());

        CONTRATO3 = new Contrato(3L, LocalDate.of(2015, 05, 15), LocalDate.of(2020, 05, 15), 1000, 500, MENSUALIDADES, new Propiedad(), new Arrendatario(), new Usuario());
       
        CONTRATOS = new ArrayList<>(Arrays.asList(CONTRATO2, CONTRATO3));
        
        ARRENDATARIO2 = new Arrendatario(2L, "ArrendatarioTest2", "apellidos test2", "00000000B", "223456789", "arrendatario2@test.com", CONTRATOS, new Usuario());

        ARRENDATARIO3 = new Arrendatario(3L, "ArrendatarioTest3", "apellidos test3", "00000000C", "323456789", "arrendatario3@test.com", CONTRATOS, new Usuario());

        ARRENDATARIOS = new ArrayList<>(Arrays.asList(ARRENDATARIO2,ARRENDATARIO3));

        DIRECCION2 = new Direccion(2L, "CalleTest2", "NúmeroTest2", "PisoTest2", "PuertaTest2", "88888", "PoblacionTest2", "provinciaTest2", "PaisTest2", null, new Usuario());

        DIRECCION3 = new Direccion(2L, "CalleTest3", "NúmeroTest3", "PisoTest3", "PuertaTest3", "77777", "PoblacionTest3", "provinciaTest3", "PaisTest3", null, new Usuario());

        DIRECCIONES = new ArrayList<>(Arrays.asList(DIRECCION2, DIRECCION3));

        PROPIEDAD2 = new Propiedad(2L, false, Propiedad.Tipo.VIVIENDA, new Direccion(), GASTOS, CONTRATOS, new Usuario());

        PROPIEDAD3 = new Propiedad(3L, false, Propiedad.Tipo.VIVIENDA, new Direccion(), GASTOS, CONTRATOS, new Usuario());

        PROPIEDADES = new ArrayList<>(Arrays.asList(PROPIEDAD2, PROPIEDAD3));
          
        USUARIO = new Usuario(1L, "UsuarioTest", "passwordTest", true, ROLES, PROPIEDADES, ARRENDATARIOS, DIRECCIONES);

        ARRENDATARIO = new Arrendatario(1L, "ArrendatarioTest", "apellidos test", "00000000A", "123456789", "arrendatario@test.com", CONTRATOS, USUARIO);

        DIRECCION = new Direccion(1L, "CalleTest", "NúmeroTest", "PisoTest", "PuertaTest", "99999", "PoblaciónTest", "ProvinciaTest", "PaisTest", null, USUARIO);

        PROPIEDAD = new Propiedad(1L, false, Propiedad.Tipo.VIVIENDA, DIRECCION, GASTOS, CONTRATOS, USUARIO);
     
        CONTRATO = new Contrato(1L, LocalDate.of(2020, 10, 12), LocalDate.of(2025, 10, 12), 1200, 600, MENSUALIDADES, PROPIEDAD, ARRENDATARIO, USUARIO);

        GASTO = new Gasto(1L, 500.0, LocalDate.of(2022, 11, 10), "GastoTest", Tipo.SEGURO, PROPIEDAD, USUARIO);

        MENSUALIDAD = new Mensualidad(1L, 1200, LocalDate.of(2021, 05, 10), true, USUARIO, CONTRATO);

    }

}
