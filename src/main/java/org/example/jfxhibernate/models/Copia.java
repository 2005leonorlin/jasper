package org.example.jfxhibernate.models;



import jakarta.persistence.*;
import lombok.Data;



import java.io.Serializable;

/**
 * Entidad que representa una copia.
 */
@Data
@Entity
public class Copia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String estado;
    private String soporte;

    @ManyToOne
    @JoinColumn(name = "id_pelicula")
    private Pelicula pelicula;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario idUsuario;

    @Override
    public String toString() {
        return "Copia{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                ", idPelicula=" + pelicula.getTitulo() +
                ", idUsuario=" + idUsuario.getNombreUsuario() +
                '}';
    }
}