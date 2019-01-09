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
import org.hibernate.Query;
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
public class Listing implements Serializable {

    private List<Author> authors;
    private Map<String, Author> authorMap;
    private List<Book> books;
    private List<Client> clients;
    private List<Book> available;
    private Map<Integer, Book> bookMap;
    private Author author;
    private Book book;
    private Client client;
    private Book newBook;
    private Book choosenBook;
    private String choosenClient;
    private String choosenAuthor;

    public Listing() {
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        books = session.createQuery("FROM Book").list();
        session.close();

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

        authorMap = new HashMap<>();
        for (Author a : authors) {
            authorMap.put(a.getName(), a);
        }
        author = authorMap.get(choosenAuthor);
    }

//    public void booksListOfClient(Client c) {       
//        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
//        Query q = session.createQuery("FROM Book WHERE Client= :par1");
//        q.setParameter("par1", client);
//        books = q.list();
//        session.close();
//    }
    public String newBook() {
        newBook = new Book();
        choosenAuthor = "";
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        authors = session.createQuery("FROM Author").list();
        session.close();
        return "newBook";
    }

    public String addBook() {
        newBook.setAuthor(author);

        if (!newBook.getTitle().isEmpty()) {
            try {
                Session session = HibernateUtil.getSessionFactory().openSession();
                Query q = session.createQuery("FROM Client WHERE id= :par1");
                q.setParameter("par1", 1);
                List list = q.list();
                client = (Client) list.get(0);
                newBook.setClient(client);
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
        newBook = null;
        author = null;
        return "newBookToAdmin";
    }

    public void deleteBook(Book b) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(b);
            session.getTransaction().commit();
            refreshBooks();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

    }

    public String updateBook(Book b) {      
        choosenBook = b;
        Session session = hibernate.HibernateUtil.getSessionFactory().openSession();
        clients = session.createQuery("FROM Client").list();
        session.close();
        return "editBook";
    }

    public void editBooksClient() {
        for (Client c : clients) {
            if (c.getName().equals(choosenClient)) {
                choosenBook.setClient(c);
            }
        }
    }

    public String saveBook() {
        editBooksClient();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(choosenBook);
        session.getTransaction().commit();
        session.close();
        refreshBooks();

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

    public Book getChoosenBook() {
        return choosenBook;
    }

    public void setChoosenBook(Book choosenBook) {
        this.choosenBook = choosenBook;
    }

}
