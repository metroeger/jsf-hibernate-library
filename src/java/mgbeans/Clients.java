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
public class Clients {

    private Client client;
    private List<Client> clients;
    private Map<String, Client> clientMap;
    private List<Book> books;
    private String choosenClient;
    private List<Author> authors;
    private Client newClient;
    private Client selected;
    private boolean newC;
    private boolean correctEmail;

    public Clients() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        clients = session.createQuery("FROM Client").list();
        session.close();
        
        newC = false;
        
        clientMap = new HashMap<>();
        for (Client c : clients) {
            clientMap.put(c.getName(), c);
        }
    }

    public void refresh() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        clients = session.createQuery("FROM Client").list();
        session.close();
    }

    public void chooseClient() {
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
    public void makeNew() {
        newClient = new Client();
        newC = !newC;
        correctEmail = true;
    }

    public void addClient() {
        if (!newClient.getName().isEmpty()) {
            if(!newClient.getEmail().contains("@")){
               correctEmail=false;              
            }
            try {
                Session session;
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
        newClient = null;
        newC=!newC;
    }

    public String updateClient(Client c) {
        selected = c;
        return "editClient";
    }

    public String saveClient() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(selected);
            session.getTransaction().commit();
            refresh();
            session.close();
            selected = null;
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return "editClientToAdmin";
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

    public Client getSelected() {
        return selected;
    }

    public void setSelected(Client selected) {
        this.selected = selected;
    }

    public boolean isNewC() {
        return newC;
    }

    public void setNewC(boolean newC) {
        this.newC = newC;
    }

    public boolean isCorrectEmail() {
        return correctEmail;
    }

    public void setCorrectEmail(boolean correctEmail) {
        this.correctEmail = correctEmail;
    }

}
