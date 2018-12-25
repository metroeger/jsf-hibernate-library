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
public class Clients implements Serializable {

    private Client client;
    private List<Client> clients;
    private Map<String, Client> clientMap;
    private List<Book> books;
    private String choosenClient;
    private List<Author> authors;
    private Client newClient = new Client();

    public Clients () {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        clients = session.createQuery("FROM Client").list();
        authors = session.createQuery("FROM Author").list();
        session.close();
        
        clientMap = new HashMap<>();
        for (Client c : clients) {
            clientMap.put(c.getName(), c);
        }
    }
  
    public void refresh(){
       Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
       clients = session.createQuery("FROM Client").list(); 
       session.close();
    }
    
    public void chooseClient(){
      client = clientMap.get(choosenClient);
      books = new ArrayList<>(client.getBooks());
    }

//    public void booksListOfClient(Client c) {       
//        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
//        Query q = session.createQuery("FROM Book WHERE Client= :par1");
//        q.setParameter("par1", client);
//        books = q.list();
//        session.close();
//    }

    public void addClient() {
        Session session;
        if (!newClient.getName().isEmpty()) {
            try {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(newClient);
                session.getTransaction().commit();
                refresh();
                session.close();
            } catch (HibernateException ex) {
                ex.printStackTrace();
                System.out.println(ex);
            }
        } 
        //newClient=null;
    }

    public void updateClient(Client c) {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(c);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteClient(Client c) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(c);
            session.getTransaction().commit();
            refresh();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }  
        
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Map<String, Client> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<String, Client> clientMap) {
        this.clientMap = clientMap;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getChoosenClient() {
        return choosenClient;
    }

    public void setChoosenClient(String choosenClient) {
        this.choosenClient = choosenClient;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Client getNewClient() {
        return newClient;
    }

    public void setNewClient(Client newClient) {
        this.newClient = newClient;
    }
    
}
