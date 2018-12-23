/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgbeans;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Author;
import pojos.Book;

/**
 *
 * @author Agi
 */
@ManagedBean
@RequestScoped
public class Authors implements Serializable {

    private Author author = new Author();
    private List<Author> authors;
    private Map<Integer, Author> authorMap = new HashMap<>();
    private List<Book> books;

    public Authors() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
//        books = session.createQuery("FROM Book").list();
//        available = session.createQuery("FROM Book").list();
        authors = session.createQuery("FROM Author").list();
//        clients = session.createQuery("FROM Client").list();
        session.close();
    }

    public void mapAuthors() {
        for (Author a : authors) {
            authorMap.put(a.getId(), a);
        }
    }
    
    public void refresh(){
       Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
       authors = session.createQuery("FROM Author").list(); 
    }

    public void booksListOfAuthor(Author a) {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery("FROM Book WHERE Author= :par1");
        q.setParameter("par1", author);
        books = q.list();
        session.close();
    }

    public void addAuthor(Author a) {
        Session session;
        if (!a.getName().isEmpty()) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(a);
                session.getTransaction().commit();
                refresh();
                session.close();
            } catch (HibernateException ex) {
                ex.printStackTrace();
                System.out.println(ex);

            }
        } else {
            System.out.println("missing data");
        }
    }

    public void updateAuthor(Author a) {

        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(a);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAuthor(Author a) {

        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(a);
            session.getTransaction().commit();
            refresh();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Map<Integer, Author> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<Integer, Author> authorMap) {
        this.authorMap = authorMap;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
