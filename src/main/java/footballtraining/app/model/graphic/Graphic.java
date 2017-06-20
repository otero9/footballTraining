package footballtraining.app.model.graphic;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import footballtraining.app.model.exercise.Exercise;

@Entity
@Table(name = "graphic")
public class Graphic implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long graphicId;
	
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "exerciseId")
	private Exercise exercise;
	
	@NotNull
	@JoinColumn(name = "graphicData")
	private String graphicData;
	
	public Graphic() {
	}

	public Graphic(Exercise exercise, String graphicData) {
		this.exercise = exercise;
		this.graphicData = graphicData;
	}
	
	public long getGraphicId() {
		return graphicId;
	}

	public void setGraphicId(long graphicId) {
		this.graphicId = graphicId;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public String getGraphicData() {
		return graphicData;
	}

	public void setGraphicData(String graphicData) {
		this.graphicData = graphicData;
	}

}
