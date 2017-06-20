package footballtraining.app.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface UserRepository.
 */
@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
	
	/**
	 * findOneByUserName
	 * @param userName
	 * @return User
	 */
	User findOneByUserName(String userName);
	
	/**
	 * findOneByEmail
	 * @param email
	 * @return User
	 */
	User findOneByEmail(String email);
	
}
