package org.example.jfxhibernate;


import org.example.jfxhibernate.models.Copia;
import org.example.jfxhibernate.models.Pelicula;
import org.example.jfxhibernate.models.Usuario;

import java.util.List;

/**
 * Clase que representa la sesión actual del usuario.
 */
public class Session {

    /**
     * Lista de copias en la sesión actual.
     */
    public static List<Copia> copyDTO = null;

    /**
     * Usuario actual en la sesión.
     */
    public static Usuario currentUser = null;

    /**
     * Película actual en la sesión.
     */
    public static Pelicula currentPelicula = null;

    /**
     * Restablece los parámetros de la sesión estableciendo todos los atributos a null.
     */
    public static void paramsnotnull() {
        currentUser = null;
        copyDTO = null;
        currentPelicula = null;
    }
}