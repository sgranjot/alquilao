package com.sgranjot.alquilao.backend.alquilao.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "propiedades")
public class Propiedad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean libre;
    private Tipo tipo;

    @OneToOne
    @JoinColumn(name = "direccion_id", unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Direccion direccion;

    @OneToMany(mappedBy = "propiedad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Gasto> gastos;

    @OneToMany(mappedBy = "propiedad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Contrato> contratos;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)                                          //Lazy indicamos que los datos del usuario solo se carguen cuando se acceda a el mejorando asi el rendimiento
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})                                          //se usa para indicar que durante la serialización o deserialización de una entidad Java, las propiedades "hibernateLazyInitializer" y "handler" deben ser ignoradas, ya que no son necesarias en el proceso de intercambio de datos con formatos como JSON
    private Usuario usuario;


    public static enum Tipo{
        OFICINA, LOCAL, VIVIENDA, NAVE, GARAJE
    }
    
}
