package org.example.jfxhibernate.models;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.*;

/**
 * Entidad que representa una película.
 */
@Data
@Entity
@Table(name = "Pelicula")
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String genero;
    @Column(name = "año")
    private Integer anho;
    private String descripcion;
    private String director;
    private String portada;

    @OneToMany(mappedBy = "pelicula", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Copia> copias = new ArrayList<>(0);

    /**
     * Agrega una copia a la lista de copias de la película.
     *
     * @param copia la copia a agregar.
     */
    public void addCopia(Copia copia) {
        copias.add(copia);
        copia.setPelicula(this);
    }
}