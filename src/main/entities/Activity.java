package main.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.StringEscapeUtils;

@Entity
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    private enum Nature {
	EXP_PRO, EXP_PERSO, STAGE, FORMATION, PROJECT, OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "year", nullable = false)
    private int year;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "nature", nullable = false)
    private Nature nature;

    @Basic(optional = false)
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "desciption")
    private String description;

    @Basic
    @Column(name = "web_address")
    private String webAddress;

    @ManyToOne(targetEntity = User.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private User user;

    public Activity() {
	super();
    }

    public Activity(int year, String nature, String title) {
	super();
	setYear(year);
	switch (nature) {
	    case "exp_pro":
		this.nature = Nature.EXP_PRO;
		break;
	    case "exp_perso":
		this.nature = Nature.EXP_PERSO;
		break;
	    case "stage":
		this.nature = Nature.STAGE;
		break;
	    case "formation":
		this.nature = Nature.FORMATION;
		break;
	    case "project":
		this.nature = Nature.PROJECT;
		break;
	    default:
		this.nature = Nature.OTHER;
	}
	setTitle(title);
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public Nature getNature() {
	return nature;
    }

    public void setNature(Nature nature) {
	this.nature = nature;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = StringEscapeUtils.escapeHtml(title);
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = StringEscapeUtils.escapeHtml(description);
    }

    public String getWebAddress() {
	return webAddress;
    }

    public void setWebAddress(String webAddress) {
	this.webAddress = StringEscapeUtils.escapeHtml(webAddress);
    }

    @Override
    public String toString() {
	return "Activity{" +
		"id=" + id +
		", year=" + year +
		", nature=" + nature +
		", title='" + title + '\'' +
		", description='" + description + '\'' +
		", webAddress='" + webAddress + '\'' +
		", user=" + user +
		'}';
    }
}
