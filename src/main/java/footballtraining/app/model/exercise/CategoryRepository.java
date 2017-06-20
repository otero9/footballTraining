package footballtraining.app.model.exercise;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	/**
	 * findOneByCategoryName
	 * @param categoryName
	 * @return Category
	 */
	Category findOneByCategoryName(String categoryName);
	
	List<Category> findAllByOrderByYearsFromAsc();

}
