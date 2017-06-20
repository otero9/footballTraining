package footballtraining.app.model.user;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;
import footballtraining.app.model.document.Article;
import footballtraining.app.model.document.Form;
import footballtraining.app.model.exercise.Exercise;
import footballtraining.app.model.video.Video;

@Entity
@Table(name = "userft")
public class User implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "userid")
	private long userId;

	@NotNull
	@Column(unique = true)
	private String userName;

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	private String name;

	@NotNull
	private String surnames;

	@NotNull
	private String password;

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date birthdate;

	@NotNull
	private String country;

	private String languages;

	private boolean authorized;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	private List<Video> videos;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	private List<Article> articles;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	private List<Form> forms;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user")
	private List<Exercise> exercises;

	public User() {
	}

	public User(String userName, String email, String name, String surnames, String password, Date birthdate,
			String country, String languages, boolean authorized) {
		this.userName = userName;
		this.email = email;
		this.name = name;
		this.surnames = surnames;
		this.password = password;
		this.birthdate = birthdate;
		this.country = country;
		this.languages = languages;
		this.authorized = authorized;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	
	/**
	 * Adds the video.
	 *
	 * @param video the video
	 */
	public void addVideo(Video video){
		videos.add(video);
		video.setUser(this);
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	public void addArticle(Article article){
		articles.add(article);
		article.setUser(this);
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	public void addForm(Form form){
		forms.add(form);
		form.setUser(this);
	}

	public List<Form> getForms() {
		return forms;
	}

	public void setForms(List<Form> forms) {
		this.forms = forms;
	}
	
	public void addExercise(Exercise exercise){
		exercises.add(exercise);
		exercise.setUser(this);
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

} // class User
