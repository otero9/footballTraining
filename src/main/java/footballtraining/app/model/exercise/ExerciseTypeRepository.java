package footballtraining.app.model.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {

	ExerciseType findOneByExerciseTypeName(String exerciseTypeName);
	
	List<ExerciseType> findAllByOrderByExerciseTypeNameAsc();
}
