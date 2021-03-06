package main.entities;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringEscapeUtils;

import main.utils.Security;

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

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Activity> cv;

    public User() {
        super();
        cv = new LinkedList<>();
    }

    public User(String name, String firstName, String email, String password) {
	super();
	setName(name);
	setFirstName(firstName);
	setEmail(email);
	setPassword(password);
	cv = new LinkedList<>();
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
	this.name = StringEscapeUtils.escapeHtml4(name);
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = StringEscapeUtils.escapeHtml4(firstName);
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = StringEscapeUtils.escapeHtml4(email);
    }

    public String getWebSite() {
	return webSite;
    }

    public void setWebSite(String webSite) {
	this.webSite = StringEscapeUtils.escapeHtml4(webSite);
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
        try {
            this.password = Security.hashPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public List<Activity> getCv() {
	return cv;
    }

    public void setCv(List<Activity> cv) {
        this.cv = cv;
        this.cv.forEach(activity -> activity.setUser(this));
    }

    public void addActivity(Activity activity) {
        activity.setUser(this);
        cv.add(activity);
    }

    public void removeActivity(Activity activity) {
        cv.remove(activity);
    }

    @Override
    public String toString() {
	return "User{" + "id=" + id + ", name='" + name + '\'' + ", firstName='" + firstName + '\'' + ", email='"
		+ email + '\'' + ", webSite='" + webSite + '\'' + ", birthDate=" + birthDate + ", password='" + password
		+ '\'' + ", cv=" + cv + '}';
    }

}
