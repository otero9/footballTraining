//package footballtraining.app.model.exercise;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class CategoryServiceTest {
//	
//	@Autowired
//	CategoryService categoryService;
//
//	@Test
//	public void contextLoads() {
//		
//	}
//
////	@Test
////	public void saveCategoryTest() {
////		Category category = new Category("peñas", 8, 9, "categoria peñas");
////		category = categoryService.save(category);
////		assertNotNull(category);
////		assertNotNull(category.getCategoryId());
////	}
////	
////	@Test
////	public void findByIdAndRemoveCategoryTest() {
////		Category category = new Category("peñas", 8, 9, "categoria peñas");
////		category = categoryService.save(category);
////		assertNotNull(category);
////		Category category2 = categoryService.findByCategoryId(category.getCategoryId());
////		assertNotNull(category2);
////		assertEquals(category.getCategoryId(), category2.getCategoryId());
////		assertEquals(category.getCategoryName(), category2.getCategoryName());
////		assertEquals(category.getCategoryDescription(), category2.getCategoryDescription());
////		assertEquals(category.getYearsFrom(), category2.getYearsFrom());
////		assertEquals(category.getYearsUntil(), category2.getYearsUntil());
////		categoryService.delete(category.getCategoryId());
////		assertNull(categoryService.findByCategoryId(category.getCategoryId()));
////	}
//	
////	@Test
////	public void findByNameAndRemoveCategoryTest() {
////		Category category = new Category("peñas", 8, 9, "categoria peñas");
////		category = categoryService.save(category);
////		Category category2 = categoryService.findByCategoryName(category.getCategoryName());
////		assertNotNull(category2);
////		assertEquals(category.getCategoryId(), category2.getCategoryId());
////		assertEquals(category.getCategoryName(), category2.getCategoryName());
////		assertEquals(category.getCategoryDescription(), category2.getCategoryDescription());
////		assertEquals(category.getYearsFrom(), category2.getYearsFrom());
////		assertEquals(category.getYearsUntil(), category2.getYearsUntil());
////	}
//
//}
