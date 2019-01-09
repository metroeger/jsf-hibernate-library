/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgbeans;

import hibernate.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Author;
import pojos.Book;
import pojos.Client;

/**
 *
 * @author Agi
 */
@ManagedBean
@SessionScoped
public class Authors implements Serializable {

    private Author author;
    private List<Author> authors;
    private List<Book> books;
    private List<Client> clients;
    private Map<String, Author> authorMap;
    private String choosenAuthor;
    private Author selected;
    private Author newAuthor;
    private boolean showNew;

    public Authors() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        authors = session.createQuery("FROM Author").list();
        showNew = false;
        session.close();

        authorMap = new HashMap<>();
        for (Author a : authors) {
            authorMap.put(a.getName(), a);
        }
    }

    public void refreshAuthors() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        authors = session.createQuery("FROM Author").list();
        session.close();
    }

    public void chooseAuthor() {
        try {
            author = authorMap.get(choosenAuthor);
            books = new ArrayList<>(author.getBooks());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void searchAuthor() {
        for (Author a : authors) {
            if (a.getName().toLowerCase().contains(choosenAuthor.toLowerCase())) {
                author = a;
            }
        }
    }

    public void newAuthor() {
        if (newAuthor == null) {
            newAuthor = new Author();
            showNew = true;
        } else {
            newAuthor = null;
            showNew = !showNew;
        }
    }

    public void addAuthor() {
        if (!newAuthor.getName().isEmpty()) {
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(newAuthor);
                session.getTransaction().commit();
                session.close();
                newAuthor = null;
                showNew = false;
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            refreshAuthors();
        }
    }

    public String updateAuthor(Author a) {
        selected = a;
        return "editAuthor";
    }

    public String saveAuthor() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(selected);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        refreshAuthors();
        return "editAuthorToAdmin";
    }

    public void deleteAuthor(Author a) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(a);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        authors.remove(a);
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

    public Map<String, Author> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<String, Author> authorMap) {
        this.authorMap = authorMap;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public String getChoosenAuthor() {
        return choosenAuthor;
    }

    public void setChoosenAuthor(String choosenAuthor) {
        this.choosenAuthor = choosenAuthor;
    }

    public Author getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
    }

    public Author getSelected() {
        return selected;
    }

    public void setSelected(Author selected) {
        this.selected = selected;
    }

    public boolean isShowNew() {
        return showNew;
    }

    public void setShowNew(boolean showNew) {
        this.showNew = showNew;
    }

}
