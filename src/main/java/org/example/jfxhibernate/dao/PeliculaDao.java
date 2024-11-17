package org.example.jfxhibernate.dao;



import org.example.jfxhibernate.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;



/**
 * Clase DAO para la entidad Pelicula.
 */
public class PeliculaDao implements DAO<Pelicula> {
    private SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el SessionFactory.
     *
     * @param sessionFactory la fábrica de sesiones de Hibernate.
     */
    public PeliculaDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Obtiene todas las películas.
     *
     * @return una lista de todas las películas.
     */
    @Override
    public List<Pelicula> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula ", Pelicula.class).list();
        } catch (Exception e) {
            return new ArrayList<Pelicula>(0);
        }
    }

    /**
     * Busca una película por su ID.
     *
     * @param id el ID de la película.
     * @return la película encontrada o null si no se encuentra.
     */
    @Override
    public Pelicula findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Pelicula.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Guarda una nueva película.
     *
     * @param pelicula la película a guardar.
     */
    @Override
    public void save(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.persist(pelicula));
    }

    /**
     * Actualiza una película existente.
     *
     * @param pelicula la película a actualizar.
     */
    @Override
    public void update(Pelicula pelicula) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(pelicula);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Elimina una película.
     *
     * @param pelicula la película a eliminar.
     */
    @Override
    public void delete(Pelicula pelicula) {
        sessionFactory.inTransaction(session -> session.remove(pelicula));
    }

    /**
     * Obtiene una lista de títulos distintos de películas.
     *
     * @return una lista de títulos distintos.
     */
    public List<String> getdistinctTitles() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct titulo from Pelicula", String.class).list();
        } catch (Exception e) {
            return new ArrayList<String>(0);
        }
    }

    /**
     * Busca una película por su título.
     *
     * @param title el título de la película.
     * @return la película encontrada o null si no se encuentra.
     */
    public Pelicula findByTitle(String title) {
        try (Session session = sessionFactory.openSession()) {
            Query<Pelicula> query = session.createQuery("from Pelicula where titulo = :titulo", Pelicula.class);
            query.setParameter("titulo", title);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene una lista de géneros distintos de películas.
     *
     * @return una lista de géneros distintos.
     */
    public List<String> getGeneros() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct genero from Pelicula", String.class).list();
        } catch (Exception e) {
            return new ArrayList<String>(0);
        }
    }

    /**
     * Obtiene una lista de géneros distintos de películas.
     *
     * @return una lista de géneros distintos.
     */
    public List<String> getDistinctGeneros() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct genero from Pelicula", String.class).list();
        } catch (Exception e) {
            return new ArrayList<String>(0);
        }
    }
}