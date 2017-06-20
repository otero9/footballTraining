package footballtraining.app.model.exercise;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import footballtraining.app.model.user.User;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
	
	List<Exercise> findByUserName(String userName);

	Page<Exercise> findByUserOrderByValorationExerciseDescDateDescExerciseIdDesc(User user, Pageable pageable);
	
	Page<Exercise> findAllByOrderByValorationExerciseDescDateDescExerciseIdDesc(Pageable pageable);
	
	Page<Exercise> findByExerciseTitleContainingIgnoreCaseOrderByValorationExerciseDescDateDescExerciseIdDesc(String exerciseTitle, Pageable pageable);
	
	Page<Exercise> findByExerciseTitleContainingIgnoreCaseAndCategoriesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(String exerciseTitle, Category category, Pageable pageable);
	
	Page<Exercise> findByExerciseTitleContainingIgnoreCaseAndCategoriesContainsAndExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(String exerciseTitle, Category category, ExerciseType exerciseType, Pageable pageable);

	Page<Exercise> findByExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(ExerciseType exerciseType, Pageable pageable);

	Page<Exercise> findByCategoriesContainsAndExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(Category category, ExerciseType exerciseType, Pageable pageable);

	Page<Exercise> findByExerciseTitleContainingIgnoreCaseAndExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(String exerciseTitle, ExerciseType exerciseType, Pageable pageable);

	Page<Exercise> findByCategoriesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(Category category, Pageable pageable);
	
}
