package footballtraining.app.model.exercise;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import footballtraining.app.model.user.User;

@Repository
public interface ExerciseValorationRepository extends JpaRepository<ExerciseValoration, Long> {
	
	List<ExerciseValoration> findAllByExerciseId(Long exerciseId);

	ExerciseValoration findByUserAndExerciseId(User user, Long exerciseId);

}
