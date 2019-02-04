package entities;

import org.apache.commons.lang.StringEscapeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "web_site", unique = true)
    private String webSite;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Basic(optional = false)
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(
            cascade = { CascadeType.MERGE, CascadeType.PERSIST },
            targetEntity = Activity.class,
            fetch = FetchType.EAGER,
            mappedBy = "user")
    private List<Activity> cv;

    public User() {
        super();
    }

    public User(String name, String firstName, String email, String password) {
        super();
        setName(name);
        setFirstName(firstName);
        setEmail(email);
        setPassword(password);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = StringEscapeUtils.escapeHtml(name);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = StringEscapeUtils.escapeHtml(firstName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = StringEscapeUtils.escapeHtml(email);
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = StringEscapeUtils.escapeHtml(webSite);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = StringEscapeUtils.escapeHtml(password);
    }

    public List<Activity> createCV() {
        return new Vector<>();
    }

    public List<Activity> getCv() {
        return cv;
    }

    public void setCv(List<Activity> cv) {
        this.cv = cv;
    }
}
