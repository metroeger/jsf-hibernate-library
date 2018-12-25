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
public class Listing implements Serializable{

    private List<Author> authors;
    private List<Book> books;
    private List<Client> clients;
    private List<Book> available;
    private Map<String, Client> clientMap;
    private Map<String, Author> authorMap;
    private Map<Integer, Book> bookMap;
    private Author author;
    private Book book;
    private Client client;
    private Author newAuthor = new Author();
    private Client newClient = new Client();
    private Book newBook = new Book();
    private Book choosenBook;
    private Author cAuthor;
    private String choosenClient;
    private String choosenAuthor;

    public Listing() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        books = session.createQuery("FROM Book").list();
        authors = session.createQuery("FROM Author").list();
        clients = session.createQuery("FROM Client").list();
        session.close();

        authorMap = new HashMap<>();
        for (Author a : authors) {
            authorMap.put(a.getName(), a);
        }
    }

    public void showAvailableBooks() {
        available.clear();
        for (Book b : books) {
            if (b.getClient().getId() == 1) {
                available.add(b);
            }
        }
    }

    public void refreshBooks() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        books = session.createQuery("FROM Book").list();
        session.close();
    }
 
    public void chooseAuthor() {
        author = authorMap.get(choosenAuthor);
        books = new ArrayList<>(author.getBooks());
    }

//    public void booksListOfClient(Client c) {       
//        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
//        Query q = session.createQuery("FROM Book WHERE Client= :par1");
//        q.setParameter("par1", client);
//        books = q.list();
//        session.close();
//    }
 

    public String addBook() {
        newBook.setAuthor(author);
        newBook.setClient(clients.get(1));
        if (!newBook.getTitle().isEmpty()) {
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.save(newBook);
                session.getTransaction().commit();
                refreshBooks();
                session.close();
            } catch (HibernateException ex) {
                ex.printStackTrace();
                System.out.println(ex);
            }
        }
        newBook=null;
        return "newBookToAdmin";
    }

    public void deleteBook(Book b) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(b);
        session.getTransaction().commit();
        //refreshBooks();
        session.close();
        books.remove(b);
        author.getBooks().remove(b);
    }
    
    public String updateBook(Book b){
        choosenBook = b;
        return "editBook";
    }

    public String saveBook(){
         Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.update(choosenBook);
                session.getTransaction().commit();
                refreshBooks();
                session.close();
                
        return "editBookToAdmin";
    }
    
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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

    public List<Book> getAvailable() {
        return available;
    }

    public void setAvailable(List<Book> available) {
        this.available = available;
    }

    public Map<String, Client> getClientMap() {
        return clientMap;
    }

    public void setClientMap(Map<String, Client> clientMap) {
        this.clientMap = clientMap;
    }

    public Map<String, Author> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<String, Author> authorMap) {
        this.authorMap = authorMap;
    }

    public Map<Integer, Book> getBookMap() {
        return bookMap;
    }

    public void setBookMap(Map<Integer, Book> bookMap) {
        this.bookMap = bookMap;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getChoosenClient() {
        return choosenClient;
    }

    public void setChoosenClient(String choosenClient) {
        this.choosenClient = choosenClient;
    }

    public String getChoosenAuthor() {
        return choosenAuthor;
    }

    public void setChoosenAuthor(String choosenAuthor) {
        this.choosenAuthor = choosenAuthor;
    }

    public Book getNewBook() {
        return newBook;
    }

    public void setNewBook(Book newBook) {
        this.newBook = newBook;
    }

    public Author getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
    }

    public Client getNewClient() {
        return newClient;
    }

    public void setNewClient(Client newClient) {
        this.newClient = newClient;
    }

    public Book getChoosenBook() {
        return choosenBook;
    }

    public void setChoosenBook(Book choosenBook) {
        this.choosenBook = choosenBook;
    }

}
