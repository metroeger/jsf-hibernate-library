/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgbeans;

import hibernate.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import pojos.Author;
import pojos.Book;
import pojos.Client;
import pojos.User;

/**
 *
 * @author Agi
 */
@ManagedBean
@SessionScoped
public class Listing {

    private List<Book> books;
    private List<Book>available;
    //private List<Book> availableBooks;
    private List<Author> authors;
    private List<Client> clients;
    private Map<Integer, Author> authorMap;
    private Map<Integer, Client> clientMap;
    private User user;
    
    public Listing() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        books = session.createQuery("FROM Book").list();
        available = session.createQuery("FROM Book").list();
        authors = session.createQuery("FROM Author").list();
        clients = session.createQuery("FROM Client").list();
        session.close();
    }
   
    public void showAvailableBooks(){
       available.clear();
       for (Book b : books){
           if (b.getClient().getId()==1){
               available.add(b);
           }
       }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer, Author> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<Integer, Author> authorMap) {
        this.authorMap = authorMap;
    }

    public Map<Integer, Client> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<Integer, Client> clientMap) {
        this.clientMap = clientMap;
    }

    public List<Book> getAvailable() {
        return available;
    }

    public void setAvailable(List<Book> available) {
        this.available = available;
    }
    
}
