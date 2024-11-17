package org.example.jfxhibernate.dao;


import org.example.jfxhibernate.HibernateUtil;
import org.example.jfxhibernate.models.Copia;
import org.example.jfxhibernate.models.Pelicula;
import org.example.jfxhibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la entidad Copia.
 */
public class CopiaDao implements DAO<Copia> {
    private SessionFactory sessionFactory;

    /**
     * Constructor que recibe una SessionFactory.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate.
     */
    public CopiaDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Encuentra todas las copias.
     *
     * @return una lista de todas las copias.
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        } catch (Exception e) {
            return new ArrayList<Copia>(0);
        }
    }

    /**
     * Encuentra una copia por su ID.
     *
     * @param id el ID de la copia.
     * @return la copia encontrada o null si no se encuentra.
     */
    @Override
    public Copia findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Copia.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guarda una nueva copia.
     *
     * @param copia la copia a guardar.
     */
    @Override
    public void save(Copia copia) {
        sessionFactory.inTransaction(session -> session.persist(copia));
    }

    /**
     * Actualiza una copia existente.
     *
     * @param copia la copia a actualizar.
     */
    @Override
    public void update(Copia copia) {
        sessionFactory.inTransaction(session -> session.merge(copia));
    }

    /**
     * Elimina una copia.
     *
     * @param copia la copia a eliminar.
     */
    @Override
    public void delete(Copia copia) {
        sessionFactory.inTransaction(session -> session.remove(copia));
    }

    /**
     * Encuentra copias por usuario.
     *
     * @param usuario el usuario cuyas copias se quieren encontrar.
     * @return una lista de copias del usuario.
     */
    public List<Copia> findByUser(Usuario usuario) {
        try (Session session = sessionFactory.openSession()) {
            Query<Copia> query = session.createQuery(
                    "from Copia where idUsuario = :idUsuario", Copia.class);
            query.setParameter("idUsuario", usuario);
            return query.list();
        }
    }

    /**
     * Obtiene una lista de estados distintos de las copias.
     *
     * @return una lista de estados distintos.
     */
    public List<String> getDistinctEstados() {
        try (Session session = sessionFactory.openSession()) {
            Query<String> query = session.createQuery(
                    "select distinct estado from Copia", String.class);
            return query.list();
        }
    }

    /**
     * Obtiene una lista de soportes distintos de las copias.
     *
     * @return una lista de soportes distintos.
     */
    public List<String> getDistinctSoportes() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct soporte from Copia", String.class).list();
        } catch (Exception e) {
            return new ArrayList<String>(0);
        }
    }

    /**
     * Elimina copias por película.
     *
     * @param pelicula la película cuyas copias se quieren eliminar.
     */
    public void deleteByPelicula(Pelicula pelicula) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Copia WHERE pelicula = :pelicula")
                    .setParameter("pelicula", pelicula)
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }
}

