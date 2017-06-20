package footballtraining.app.model.document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import footballtraining.app.model.user.User;

@Repository
public interface FormValorationRepository extends JpaRepository<FormValoration, Long> {

	List<FormValoration> findAllByFormId(Long formId);
	
	FormValoration findByUserAndFormId(User user, Long formId);
	
}
