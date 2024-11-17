package org.example.jfxhibernate.dao;




import org.example.jfxhibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;

import java.util.List;
/**
 * Clase UserDao que implementa la interfaz DAO para la entidad Usuario.
 */
public class UserDao implements DAO<Usuario> {
    private SessionFactory sessionFactory;

    /**
     * Constructor de UserDao.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate.
     */
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Encuentra todos los usuarios.
     *
     * @return una lista de todos los usuarios.
     */
    @Override
    public List<Usuario> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Usuario", Usuario.class).list();
        }
    }

    /**
     * Encuentra un usuario por su ID.
     *
     * @param id el ID del usuario.
     * @return el usuario encontrado o null si no se encuentra.
     */
    @Override
    public Usuario findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Usuario.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guarda un nuevo usuario.
     *
     * @param usuario el usuario a guardar.
     */
    @Override
    public void save(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.persist(usuario));
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param usuario el usuario a actualizar.
     */
    @Override
    public void update(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.merge(usuario));
    }

    /**
     * Elimina un usuario.
     *
     * @param usuario el usuario a eliminar.
     */
    @Override
    public void delete(Usuario usuario) {
        sessionFactory.inTransaction(session -> session.remove(usuario));
    }

    /**
     * Valida un usuario por su nombre de usuario y contraseña.
     *
     * @param user el nombre de usuario.
     * @param pass la contraseña.
     * @return el usuario validado o null si no se encuentra.
     */
    public Usuario validarUsuario(String user, String pass) {
        try (Session session = sessionFactory.openSession()) {
            Query<Usuario> q = session.createQuery("select u from Usuario u where u.nombreUsuario = :user and u.contraseña = :pass", Usuario.class);

            q.setParameter("user", user);
            q.setParameter("pass", pass);
            return q.list().getFirst();
        }
    }
}
