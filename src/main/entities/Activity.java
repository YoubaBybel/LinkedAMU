package main.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.apache.commons.lang3.StringEscapeUtils;

@Entity
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Nature {
		EXP_PRO, EXP_PERSO, STAGE, FORMATION, PROJET, AUTRE
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

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name="user_id")
	private User user;

	public Activity() {
		super();
	}

	public Activity(int year, String nature, String title) {
		super();
		setYear(year);
		switch (nature.toLowerCase()) {
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
			this.nature = Nature.PROJET;
			break;
		default:
			this.nature = Nature.AUTRE;
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
		return StringEscapeUtils.unescapeHtml4(title);
	}

	public void setTitle(String title) {
		this.title = StringEscapeUtils.escapeHtml4(title);
	}

	public String getDescription() {
		return StringEscapeUtils.unescapeHtml4(description);
	}

	public void setDescription(String description) {
		this.description = StringEscapeUtils.escapeHtml4(description);
	}

	public String getWebAddress() {
		return StringEscapeUtils.unescapeHtml4(webAddress);
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = StringEscapeUtils.escapeHtml4(webAddress);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Activity{" + "id=" + id + ", year=" + year + ", nature=" + nature + ", title='" + title + '\''
				+ ", description='" + description + '\'' + ", webAddress='" + webAddress + '\'' + ", user_id="
				+ (user == null ? "null" : user.getId().toString()) + '}';
	}

}
