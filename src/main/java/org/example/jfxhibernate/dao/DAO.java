package org.example.jfxhibernate.dao;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD.
 *
 * @param <T> el tipo de entidad.
 */
public interface DAO<T> {
    /**
     * Encuentra todas las entidades.
     *
     * @return una lista de todas las entidades.
     */
    public List<T> findAll();

    /**
     * Encuentra una entidad por su ID.
     *
     * @param id el ID de la entidad.
     * @return la entidad encontrada o null si no se encuentra.
     */
    public T findById(Long id);

    /**
     * Guarda una nueva entidad.
     *
     * @param t la entidad a guardar.
     */
    public void save(T t);

    /**
     * Actualiza una entidad existente.
     *
     * @param t la entidad a actualizar.
     */
    public void update(T t);

    /**
     * Elimina una entidad.
     *
     * @param t la entidad a eliminar.
     */
    public void delete(T t);
}
