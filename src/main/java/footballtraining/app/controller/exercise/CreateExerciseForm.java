package footballtraining.app.controller.exercise;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotBlank;
import footballtraining.app.model.exercise.Exercise;

public class CreateExerciseForm {

	private static final String NOT_BLANK_MESSAGE = "Fill in the field";
	
	@NotBlank(message = CreateExerciseForm.NOT_BLANK_MESSAGE)
	private String exerciseTitle;
	
	@NotBlank(message = CreateExerciseForm.NOT_BLANK_MESSAGE)
	private String exerciseDescription;
	
	@NotBlank(message = CreateExerciseForm.NOT_BLANK_MESSAGE)
	private String exerciseObjective;
	
	private String loads;
	
	private String breaks;
	
	private String videoURL;

	private boolean prebenjamin;
	
	private boolean benjamin;
	
	private boolean fiddle;
	
	private boolean child;
	
	private boolean cadet;
	
	private boolean youth;
	
	private boolean senior;
	
	private boolean technique;
	
	private boolean tactic;
	
	private boolean physical;
	
	private boolean coordination;
	
	private boolean force;
	
	private boolean other;
	
	private boolean addGraphic;
	
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

	public boolean isPrebenjamin() {
		return prebenjamin;
	}

	public void setPrebenjamin(boolean prebenjamin) {
		this.prebenjamin = prebenjamin;
	}

	public boolean isBenjamin() {
		return benjamin;
	}

	public void setBenjamin(boolean benjamin) {
		this.benjamin = benjamin;
	}

	public boolean isFiddle() {
		return fiddle;
	}

	public void setFiddle(boolean fiddle) {
		this.fiddle = fiddle;
	}

	public boolean isChild() {
		return child;
	}

	public void setChild(boolean child) {
		this.child = child;
	}

	public boolean isCadet() {
		return cadet;
	}

	public void setCadet(boolean cadet) {
		this.cadet = cadet;
	}

	public boolean isYouth() {
		return youth;
	}

	public void setYouth(boolean youth) {
		this.youth = youth;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public boolean isTechnique() {
		return technique;
	}

	public void setTechnique(boolean technique) {
		this.technique = technique;
	}

	public boolean isTactic() {
		return tactic;
	}

	public void setTactic(boolean tactic) {
		this.tactic = tactic;
	}

	public boolean isPhysical() {
		return physical;
	}

	public void setPhysical(boolean physical) {
		this.physical = physical;
	}

	public boolean isCoordination() {
		return coordination;
	}

	public void setCoordination(boolean coordination) {
		this.coordination = coordination;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}
	
	public Exercise creExercise() {
		return null;
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

	public boolean isAddGraphic() {
		return addGraphic;
	}

	public void setAddGraphic(boolean addGraphic) {
		this.addGraphic = addGraphic;
	}

	public ExerciseDTO createExercise() {
		
		List<String> categories = new ArrayList<String>();
		List<String> exerciseTypes = new ArrayList<String>();
		
		if (prebenjamin){
			categories.add("Prebenjamin");
		}
		if (benjamin){
			categories.add("Benjamin");
		}
		if (fiddle){
			categories.add("Fiddle");
		}
		if (child){
			categories.add("Child");
		}
		if (cadet){
			categories.add("Cadet");
		}
		if (youth){
			categories.add("Youth");
		}
		if (senior){
			categories.add("Senior");
		}
		if (technique){
			exerciseTypes.add("Technique");
		}
		if (tactic){
			exerciseTypes.add("Tactic");
		}
		if (physical){
			exerciseTypes.add("Physical");
		}
		if (coordination){
			exerciseTypes.add("Coordination");
		}
		if (force){
			exerciseTypes.add("Force");
		}
		if (other){
			exerciseTypes.add("Other");
		}
		if (categories.isEmpty()){
			
		}
		if (exerciseTypes.isEmpty()){
			
		}
				
		return new ExerciseDTO(exerciseTitle, exerciseDescription, exerciseObjective, loads, breaks, videoURL, categories, exerciseTypes);
		
	}
	
}
