package com.sgranjot.alquilao.backend.alquilao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;
import com.sgranjot.alquilao.backend.alquilao.model.Contrato;
import com.sgranjot.alquilao.backend.alquilao.model.Direccion;
import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;
import com.sgranjot.alquilao.backend.alquilao.model.Rol;
import com.sgranjot.alquilao.backend.alquilao.model.Usuario;

public class DataDummy {

    public static final Usuario USUARIO;
    
    public static final Arrendatario ARRENDATARIO, ARRENDATARIO2, ARRENDATARIO3;

    public static final List<Contrato> CONTRATOS;

    public static final List<Rol> ROLES;
    
    public static final List<Propiedad> PROPIEDADES;

    public static final List<Arrendatario> ARRENDATARIOS;

    public static final List<Direccion> DIRECCIONES;


    static {
 
        ROLES = new ArrayList<>();
        
        PROPIEDADES = new ArrayList<>();

        DIRECCIONES = new ArrayList<>();

        CONTRATOS = new ArrayList<>();

        ARRENDATARIO2 = new Arrendatario(2L, "ArrendatarioTest2", "apellidos test2", "00000000B", "223456789", "arrendatario2@test.com", CONTRATOS, new Usuario());

        ARRENDATARIO3 = new Arrendatario(3L, "ArrendatarioTest3", "apellidos test3", "00000000C", "323456789", "arrendatario3@test.com", CONTRATOS, new Usuario());
       
        ARRENDATARIOS = new ArrayList<>(Arrays.asList(ARRENDATARIO2,ARRENDATARIO3));
       
        USUARIO = new Usuario(1L, "UsuarioTest", "passwordTest", true, ROLES, PROPIEDADES, ARRENDATARIOS, DIRECCIONES);

        ARRENDATARIO = new Arrendatario(1L, "ArrendatarioTest", "apellidos test", "00000000A", "123456789", "arrendatario@test.com", CONTRATOS, USUARIO);
     
        

    }

}
