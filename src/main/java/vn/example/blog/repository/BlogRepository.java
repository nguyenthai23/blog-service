package vn.example.blog.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import vn.example.blog.entity.Blog;

import java.util.List;

public class BlogRepository {
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public Blog save(Blog blog) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Long id = (Long) session.save(blog);
            session.getTransaction().commit();
            return session.load(Blog.class, id);
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Blog findById(long id) {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM Blog where id = :id");
            query.setParameter("id", id);
            Blog blog = (Blog) query.getSingleResult();
            return blog;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public List<Blog> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Blog").list();
    }

    public Blog update(Blog blog) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(blog);
            session.getTransaction().commit();
            return session.load(Blog.class, blog.getId());
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void delete(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Blog customer = session.load(Blog.class, id);
            session.delete(customer);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
    }
}
