package org.example.jfxhibernate.models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Representa un usuario en el sistema.
 */
@Data
@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    private String contraseña;
    private boolean admin;

    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.EAGER)
    private List<Copia> copias = new ArrayList<>(0);

    /**
     * Agrega una copia a la lista de copias del usuario.
     *
     * @param copia la copia a agregar.
     */
    public void addCopia(Copia copia) {
        copias.add(copia);
        copia.setIdUsuario(this);
    }
}