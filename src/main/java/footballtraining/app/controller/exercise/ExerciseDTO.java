package footballtraining.app.controller.exercise;

import java.util.List;

public class ExerciseDTO {

	private String exerciseTitle;

	private String exerciseDescription;
	
	private String exerciseObjective;
	
	private String loads;
	
	private String breaks;

	private String videoURL;
	
	private List<String> categories;
	
	private List<String> exerciseTypes;
	
	public ExerciseDTO(String exerciseTitle, String exerciseDescription, String exerciseObjective, String loads, String breaks, String videoURL, List<String> categories, List<String> exerciseTypes){
		this.exerciseTitle = exerciseTitle;
		this.exerciseDescription = exerciseDescription;
		this.exerciseObjective = exerciseObjective;
		this.loads = loads;
		this.breaks = breaks;
		this.videoURL = videoURL;
		this.categories = categories;
		this.exerciseTypes = exerciseTypes;
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

	public String getExerciseObjective() {
		return exerciseObjective;
	}

	public void setExerciseObjective(String exerciseObjective) {
		this.exerciseObjective = exerciseObjective;
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

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getExerciseTypes() {
		return exerciseTypes;
	}

	public void setExerciseTypes(List<String> exerciseTypes) {
		this.exerciseTypes = exerciseTypes;
	}
	
	
	
}
