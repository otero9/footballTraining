package footballtraining.app.model.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import footballtraining.app.model.exceptions.SaveNotAvailableException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Test
	public void saveUserTest() {
		User user = new User("oteroc", "oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
		user = userService.save(user);
		assertNotNull(user.getUserId());
	}
	
	@Test
	public void findByUserNameTest() {
		User user = new User("oteroc", "oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
		user = userService.save(user);
		assertNotNull(user.getUserId());
		User user2 = userService.findByUserName(user.getUserName());
		assertEquals(user.getUserName(),user2.getUserName());
		assertEquals(user.getName(),user2.getName());
		assertEquals(user.getEmail(),user2.getEmail());
		assertEquals(user.getSurnames(),user2.getSurnames());
		assertEquals(user.getCountry(),user2.getCountry());
		assertEquals(user.getLanguages(),user2.getLanguages());
	}
	
	@Test(expected = SaveNotAvailableException.class)
	public void saveUserWithExistingUserNameTest() {
		User user = new User("oteroc", "oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
		userService.save(user);
		userService.save(user);
	}
	
	@Test(expected = SaveNotAvailableException.class)
	public void saveUserWithExistingMailTest() {
		User user = new User("oteroc", "oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
		User user2 = new User("adrianc", "oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
		userService.save(user);
		userService.save(user2);
	}
	
}
