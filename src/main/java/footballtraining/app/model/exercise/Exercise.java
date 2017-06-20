package footballtraining.app.model.exercise;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import footballtraining.app.model.graphic.Graphic;
import footballtraining.app.model.user.User;


@Entity
@Table(name = "exercise")
public class Exercise implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long exerciseId;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	@NotNull
	private String exerciseTitle;

	private String exerciseDescription;
	
	private String exerciseObjective;
	
	private String loads;
	
	private String breaks;

	private String videoURL;
	
	@ManyToMany
	@JoinTable(name = "category_exercise", joinColumns = { @JoinColumn(name = "exerciseId") }, inverseJoinColumns = { @JoinColumn(name = "categoryId") })
	@OrderBy("yearsFrom ASC")
	private Set<Category> categories = new HashSet<Category>();
	
	@ManyToMany
	@JoinTable(name = "exercisetype_exercise", joinColumns = { @JoinColumn(name = "exerciseId") }, inverseJoinColumns = { @JoinColumn(name = "exerciseTypeId") })
	@OrderBy("exerciseTypeName ASC")
	private Set<ExerciseType> exerciseTypes = new HashSet<ExerciseType>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne(mappedBy = "exercise")
	private Graphic graphic;
	
	@NotNull
    @Column(name="date")
    private Date date;

	private Float valorationExercise;

	private Integer valorationCountExercise;

	public Exercise() {
	}

	public Exercise(User user, String exerciseTitle, String exerciseDescription, String exerciseObjective, String loads, String breaks, String videoURL, Graphic graphic, Float valorationExercise, Integer valorationCountExercise, Set<Category> categories, Set<ExerciseType> exerciseTypes) {
		this.user = user;
		this.exerciseTitle = exerciseTitle;
		this.exerciseDescription = exerciseDescription;
		this.exerciseObjective = exerciseObjective;
		this.loads = loads;
		this.breaks = breaks;
		this.videoURL = videoURL;
		this.valorationExercise = valorationExercise;
		this.valorationCountExercise = valorationCountExercise;
		this.categories = categories;
		this.exerciseTypes = exerciseTypes;
		this.graphic = graphic;
		this.date = new Date();
	}
	
	public Exercise(User user, String exerciseTitle, String exerciseDescription, String exerciseObjective, String loads, String breaks, String videoURL, Float valorationExercise, Integer valorationCountExercise) {
		this.user = user;
		this.exerciseTitle = exerciseTitle;
		this.exerciseDescription = exerciseDescription;
		this.exerciseObjective = exerciseObjective;
		this.loads = loads;
		this.breaks = breaks;
		this.videoURL = videoURL;
		this.valorationExercise = valorationExercise;
		this.valorationCountExercise = valorationCountExercise;
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(long exerciseId) {
		this.exerciseId = exerciseId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getExerciseTitle() {
		return exerciseTitle;
	}

	public void setExerciseTitle(String exerciseTitle) {
		this.exerciseTitle = exerciseTitle;
	}

	public String getExerciseDescription() {
		return exerciseDescription;
	}

	public void setExerciseDescription(String exerciseDescription) {
		this.exerciseDescription = exerciseDescription;
	}

	public String getLoads() {
		return loads;
	}

	public void setLoads(String loads) {
		this.loads = loads;
	}

	public String getBreaks() {
		return breaks;
	}

	public void setBreaks(String breaks) {
		this.breaks = breaks;
	}

	public String getVideoURL() {
		return videoURL;
	}

	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}

	public Float getValorationExercise() {
		return valorationExercise;
	}

	public void setValorationExercise(Float valorationExercise) {
		this.valorationExercise = valorationExercise;
	}

	public Integer getValorationCountExercise() {
		return valorationCountExercise;
	}

	public void setValorationCountExercise(Integer valorationCountExercise) {
		this.valorationCountExercise = valorationCountExercise;
	}
	
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	
	public Set<ExerciseType> getExerciseTypes() {
		return this.exerciseTypes;
	}

	public void setExerciseTypes(Set<ExerciseType> exerciseTypes) {
		this.exerciseTypes = exerciseTypes;
	}

	public String getExerciseObjective() {
		return exerciseObjective;
	}

	public void setExerciseObjective(String exerciseObjective) {
		this.exerciseObjective = exerciseObjective;
	}

	public Graphic getGraphic() {
		return graphic;
	}

	public void setGraphic(Graphic graphic) {
		this.graphic = graphic;
	}

}
