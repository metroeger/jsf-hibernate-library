package pojos;
// Generated 2018.12.23. 13:36:52 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Book generated by hbm2java
 */
@Entity
@Table(name="book"
    ,catalog="booklibrary"
)
public class Book  implements java.io.Serializable {


     private Integer id;
     private Author author;
     private Client client;
     private String title;
     private Byte taken;

    public Book() {
    }

	
    public Book(Author author, Client client) {
        this.author = author;
        this.client = client;
    }
    public Book(Author author, Client client, String title, Byte taken) {
       this.author = author;
       this.client = client;
       this.title = title;
       this.taken = taken;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="authorId", nullable=false)
    public Author getAuthor() {
        return this.author;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="clientId", nullable=false)
    public Client getClient() {
        return this.client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }

    
    @Column(name="title", length=45)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="taken")
    public Byte getTaken() {
        return this.taken;
    }
    
    public void setTaken(Byte taken) {
        this.taken = taken;
    }




}

