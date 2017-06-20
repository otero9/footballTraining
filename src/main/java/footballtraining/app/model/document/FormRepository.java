package footballtraining.app.model.document;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import footballtraining.app.model.user.User;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

	Form findByFormTitle(String formTitle);
	
	List<Form> findAllByOrderByDateDesc();
	
	Page<Form> findAllByOrderByValorationFormDescDateDescFormIdDesc(Pageable pageable);

	Page<Form> findByFormTitleContainingIgnoreCaseOrderByValorationFormDescDateDescFormIdDesc(String formTitle, Pageable pageable);

	Page<Form> findByUserOrderByValorationFormDescDateDescFormIdDesc(User user, Pageable pageable);

}
