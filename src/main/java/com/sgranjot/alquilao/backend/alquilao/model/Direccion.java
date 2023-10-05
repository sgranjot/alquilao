package com.sgranjot.alquilao.backend.alquilao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "direcciones")
public class Direccion implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
    private String numero;
    private String piso;
    private String puerta;
    @Column(name = "codigo_postal")
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String pais;
    @Column(name = "numero_plaza")
    private String numeroPlaza;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  
    @JoinColumn(name = "usuario_id")                                        
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

}
