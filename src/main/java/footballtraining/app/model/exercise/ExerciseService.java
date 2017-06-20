package footballtraining.app.model.exercise;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import footballtraining.app.model.comment.Comment;
import footballtraining.app.model.exceptions.DeleteNotAvailableException;
import footballtraining.app.model.exceptions.ExerciseTypeNotExistsException;
import footballtraining.app.model.exceptions.UserNotExistsException;
import footballtraining.app.model.graphic.Graphic;
import footballtraining.app.model.user.User;

/**
 * The Interface ExerciseService.
 */
public interface ExerciseService {

	Exercise save(Exercise exercise) throws UserNotExistsException;
	
	Exercise update(Exercise exercise) throws UserNotExistsException;
	
	Exercise findById(Long exerciseId);
	
	List<Exercise> findByExerciseType(String exerciseTypeName) throws ExerciseTypeNotExistsException;
	
	Page<Exercise> findAllExercise(Pageable pageable, String keywords, String selectedCategory, String selectedExerciseType);
	
	Page<Exercise> findUserExercises(User user, Pageable pageable);
	
	void delete(Exercise exercise) throws UserNotExistsException, DeleteNotAvailableException;
	
	void addGraphicToExercise(Graphic graphic,Exercise exercise) throws UserNotExistsException;
	
	void removeGraphicToExercise(Exercise exercise) throws UserNotExistsException ;
	
	void updateGraphicToExercise(Graphic graphic,Exercise exercise) throws UserNotExistsException;
	
	void addComment(Comment comment, Exercise exercise) throws UserNotExistsException;
	
	void deleteComment(Comment comment, Exercise exercise) throws UserNotExistsException;
	
	ExerciseType findByExerciseTypeId(Long exerciseTypeId);
	
	ExerciseType findByExerciseTypeName(String exerciseTypeName);
	
	List<ExerciseType> getAllExerciseTypes();
	
	Category save(Category category);
	
	Category findByCategoryId(Long categoryId);
	
	Category findByCategoryName(String categoryName);
	
	void delete(Long categoryId);
	
	List<Category> getAllCategories();
	
	
}
