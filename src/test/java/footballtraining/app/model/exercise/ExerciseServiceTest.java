//package footballtraining.app.model.exercise;
//
//import static org.junit.Assert.*;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import footballtraining.app.model.user.User;
//import footballtraining.app.model.user.UserService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class ExerciseServiceTest {
//
//	@Autowired
//	ExerciseService exerciseService;
//	
//	@Autowired
//	ExerciseRepository exerciseRepository;
//	
//	@Autowired
//	ExerciseTypeRepository exerciseTypeRepository;
//	
//	@Autowired
//	UserService userService;
//	
//	@Autowired
//	CategoryService categoryService;
//	
//	@Test
//	public void saveExerciseTest() {
//		User user = new User("a.oteroc", "a.oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
//		user = userService.save(user);
//		assertNotNull(user.getUserId());
//		
//		Exercise exercise = new Exercise(user, "posesion", "2vs2", "", null, null, null, 0, 0);
//		
//		exercise = exerciseService.save(exercise);
//		assertNotNull(exercise.getExerciseId());
//	}
//	
//	@Test
//	public void updateExerciseTest() {
//		User user = new User("a.oteroc", "a.oteroc@udc.es", "adrian", "otero calviño", "oteroCulleredo", new Date(), "españa", "esp", false);
//		user = userService.save(user);
//		assertNotNull(user.getUserId());
//		
//		Exercise exercise = new Exercise(user, "posesion", "2vs2", "",null, null, null, null, 0, 0, null, null);
//		
//		exercise = exerciseService.save(exercise);
//		assertNotNull(exercise.getExerciseId());
//		
////		Category category = categoryService.findByCategoryName("Benjamin");
////		ExerciseType exerciseType = exerciseTypeRepository.findOneByExerciseTypeName("Tactica");
////		
////		List<Category> categories = new ArrayList<Category>();
////		List<ExerciseType> exerciseTypes = new ArrayList<ExerciseType>();
////		categories.add(category);
////		exerciseTypes.add(exerciseType);
////		exercise.setCategories(categories);
////		exercise.setExerciseTypes(exerciseTypes);
////		exercise = exerciseService.update(exercise);
////		assertEquals(exercise.getCategories().size(), 1);
////		
////		assertNotNull(exerciseService.findByExerciseType(exerciseType.getExerciseTypeName()));
//		
//	}
//	
//}
