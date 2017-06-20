package footballtraining.app.model.exercise;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exercisetype")
public class ExerciseType implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long exerciseTypeId;
	
	@NotNull
	private String exerciseTypeName;
	
	@NotNull
	private String exerciseTypeDescription;
	
	public ExerciseType() {
	}

	public ExerciseType(String exerciseTypeName, String exerciseTypeDescription) {
		this.exerciseTypeName = exerciseTypeName;
		this.exerciseTypeDescription = exerciseTypeDescription;
	}

	public long getExerciseTypeId() {
		return exerciseTypeId;
	}

	public void setExerciseTypeId(long exerciseTypeId) {
		this.exerciseTypeId = exerciseTypeId;
	}

	public String getExerciseTypeName() {
		return exerciseTypeName;
	}

	public void setExerciseTypeName(String exerciseTypeName) {
		this.exerciseTypeName = exerciseTypeName;
	}

	public String getExerciseTypeDescription() {
		return exerciseTypeDescription;
	}

	public void setExerciseTypeDescription(String exerciseTypeDescription) {
		this.exerciseTypeDescription = exerciseTypeDescription;
	}
	
}
