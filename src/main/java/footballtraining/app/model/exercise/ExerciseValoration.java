package footballtraining.app.model.exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import footballtraining.app.model.user.User;

@Entity
@Table(name = "exercisevaloration")
public class ExerciseValoration implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long exerciseValorationId;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
    @Column(name="exerciseId")
	@NotNull
	private long exerciseId;
	
	@NotNull
	private int valorationValue;
	
	public ExerciseValoration() {
	}

	public ExerciseValoration(User user, long exerciseId, int valorationValue) {
		this.user = user;
		this.exerciseId = exerciseId;
		this.valorationValue = valorationValue;
	}

	public long getExerciseValorationId() {
		return exerciseValorationId;
	}

	public void setExerciseValorationId(long exerciseValorationId) {
		this.exerciseValorationId = exerciseValorationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getExerciseId() {
		return exerciseId;
	}

	public void setExerciseId(long exerciseId) {
		this.exerciseId = exerciseId;
	}

	public int getValorationValue() {
		return valorationValue;
	}

	public void setValorationValue(int valorationValue) {
		this.valorationValue = valorationValue;
	}

}