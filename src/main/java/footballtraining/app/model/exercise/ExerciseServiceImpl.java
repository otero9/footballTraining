package footballtraining.app.model.exercise;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import footballtraining.app.model.comment.Comment;
import footballtraining.app.model.comment.CommentRepository;
import footballtraining.app.model.exceptions.DeleteNotAvailableException;
import footballtraining.app.model.exceptions.ExerciseTypeNotExistsException;
import footballtraining.app.model.exceptions.UserNotExistsException;
import footballtraining.app.model.graphic.Graphic;
import footballtraining.app.model.graphic.GraphicRepository;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;

/**
 * The Class ExerciseService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExerciseServiceImpl implements ExerciseService {
	
	@Autowired
	private ExerciseTypeRepository exerciseTypeRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private GraphicRepository graphicRepository;
	
	@Autowired
	private ExerciseValorationRepository exerciseValorationRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Transactional(readOnly=true)
	public Exercise findById(Long exerciseId) {
		return exerciseRepository.getOne(exerciseId);
	}
	
	@Transactional
	public Exercise save(Exercise exercise) throws UserNotExistsException{
		if (userService.find(exercise.getUser()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		return exerciseRepository.save(exercise);
	}
	
	@Transactional
	public Exercise update(Exercise exercise) throws UserNotExistsException{
		if (userService.find(exercise.getUser()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		return exerciseRepository.save(exercise);
	}
	
	@Transactional(readOnly=true)
	public List<Exercise> findByUserName(String userName) throws UserNotExistsException{
		if (userService.findByUserName(userName) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		return exerciseRepository.findByUserName(userName);
	}
	
	@Transactional
	public void delete(Exercise exercise) throws UserNotExistsException, DeleteNotAvailableException {
		if (userService.findByUserName(exercise.getUser().getUserName()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		if (exerciseRepository.getOne(exercise.getExerciseId())!=null) {
			List<ExerciseValoration> valorations = exerciseValorationRepository.findAllByExerciseId(exercise.getExerciseId());
			if ((valorations!=null) && (!valorations.isEmpty())) {
				for (ExerciseValoration valoration : valorations) {
					exerciseValorationRepository.delete(valoration);
				}
			}
			List<Comment> comments = commentRepository.findByExerciseIdOrderByDateDesc(exercise.getExerciseId());
			if ((comments!=null) && (!comments.isEmpty())) {
				for (Comment comment : comments) {
					commentRepository.delete(comment);
				}
			}
			if (exercise.getGraphic()!=null) {
				graphicRepository.delete(exercise.getGraphic().getGraphicId());
			}
			exerciseRepository.delete(exercise.getExerciseId());
		} else{
			throw new DeleteNotAvailableException("No se puede eliminar video no perteneciente");
		}
	}
	
	@Transactional
	public void addGraphicToExercise(Graphic graphic,Exercise exercise) throws UserNotExistsException {
		if (userService.findByUserName(exercise.getUser().getUserName()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		if (exercise.getGraphic()!=null) {
			graphicRepository.delete(exercise.getGraphic().getGraphicId());
		}
		graphic.setExercise(exercise);
		Graphic graphic2 = graphicRepository.save(graphic);
		exercise.setGraphic(graphic2);
		exerciseRepository.save(exercise);
	}
	
	@Transactional
	public void removeGraphicToExercise(Exercise exercise) throws UserNotExistsException {
		if (userService.findByUserName(exercise.getUser().getUserName()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		if (exercise.getGraphic()!=null) {
			graphicRepository.delete(exercise.getGraphic().getGraphicId());
		}
		exercise.setGraphic(null);
		exerciseRepository.save(exercise);
	}
	
	@Transactional
	public void updateGraphicToExercise(Graphic graphic,Exercise exercise) throws UserNotExistsException {
		if (userService.findByUserName(exercise.getUser().getUserName()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		if (exercise.getGraphic()!=null) {
			graphicRepository.delete(exercise.getGraphic().getGraphicId());
			graphic.setExercise(exercise);
			Graphic graphic2 = graphicRepository.save(graphic);
			exercise.setGraphic(graphic2);
			exerciseRepository.save(exercise);
		}
	}

	@Transactional(readOnly=true)
	public List<Exercise> findByExerciseType(String exerciseTypeName) throws ExerciseTypeNotExistsException{
		ExerciseType exerciseType = exerciseTypeRepository.findOneByExerciseTypeName(exerciseTypeName);
		if (exerciseType == null){
			throw new ExerciseTypeNotExistsException("El nombre de tipo de ejercicio no existe");	
		}
		List<Exercise> result = new ArrayList<Exercise>();
		List<Exercise> exercises = exerciseRepository.findAll();
		for (Exercise exercise : exercises){
			if ((exercise.getExerciseTypes()!=null)&&(!exercise.getExerciseTypes().isEmpty())){
				if (exercise.getExerciseTypes().contains(exerciseType)){
					result.add(exercise);
				}
			}
		}
		if (!result.isEmpty()){
			return result;
		}
		return null;
	}
	
	@Transactional(readOnly=true)
	public Page<Exercise> findAllExercise(Pageable pageable, String keywords, String selectedCategory,
			String selectedExerciseType) {

		if ((keywords == null) || (keywords.isEmpty())) {
			keywords = null;
		}

		Long categoryId = null;
		Category category = null;

		if ((selectedCategory != null) && (!selectedCategory.isEmpty())) {
			categoryId = Long.valueOf(selectedCategory);
			category = categoryRepository.getOne(categoryId);
		}

		Long exerciseTypeId = null;
		ExerciseType exerciseType = null;

		if ((selectedExerciseType != null) && (!selectedExerciseType.isEmpty())) {
			exerciseTypeId = Long.valueOf(selectedExerciseType);
			exerciseType = exerciseTypeRepository.getOne(exerciseTypeId);
		}

		if ((keywords == null) && (categoryId == null) && (exerciseTypeId == null)) {
			return exerciseRepository.findAllByOrderByValorationExerciseDescDateDescExerciseIdDesc(pageable);
		}

		if ((keywords != null) && (categoryId == null) && (exerciseTypeId == null)) {
			return exerciseRepository
					.findByExerciseTitleContainingIgnoreCaseOrderByValorationExerciseDescDateDescExerciseIdDesc(
							keywords, pageable);
		}

		if ((keywords != null) && (categoryId != null) && (exerciseTypeId == null)) {
			return exerciseRepository
					.findByExerciseTitleContainingIgnoreCaseAndCategoriesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(
							keywords, category, pageable);
		}

		if ((keywords != null) && (categoryId != null) && (exerciseTypeId != null)) {
			return exerciseRepository
					.findByExerciseTitleContainingIgnoreCaseAndCategoriesContainsAndExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(
							keywords, category, exerciseType, pageable);
		}

		if ((keywords == null) && (categoryId == null) && (exerciseTypeId != null)) {
			return exerciseRepository.findByExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(
					exerciseType, pageable);
		}

		if ((keywords == null) && (categoryId != null) && (exerciseTypeId != null)) {
			return exerciseRepository
					.findByCategoriesContainsAndExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(
							category, exerciseType, pageable);
		}

		if ((keywords != null) && (categoryId == null) && (exerciseTypeId != null)) {
			return exerciseRepository
					.findByExerciseTitleContainingIgnoreCaseAndExerciseTypesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(
							keywords, exerciseType, pageable);
		}

		if ((keywords == null) && (categoryId != null) && (exerciseTypeId == null)) {
			return exerciseRepository
					.findByCategoriesContainsOrderByValorationExerciseDescDateDescExerciseIdDesc(category, pageable);
		}

		return null;

	}
	
	@Transactional(readOnly=true)
	public Page<Exercise> findUserExercises(User user, Pageable pageable) {
		
		return exerciseRepository
		.findByUserOrderByValorationExerciseDescDateDescExerciseIdDesc(user, pageable);
	}
	
	@Transactional
	public void addComment(Comment comment, Exercise exercise) throws UserNotExistsException {
		if (userService.findByUserName(exercise.getUser().getUserName()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		commentRepository.save(comment);
	}
	
	@Transactional
	public void deleteComment(Comment comment, Exercise exercise) throws UserNotExistsException {
		if (userService.findByUserName(exercise.getUser().getUserName()) == null){
			throw new UserNotExistsException("El nombre de usuario no existe");	
		}
		commentRepository.delete(comment);
	}
	
	@Transactional
	public Category save(Category category){
		return categoryRepository.save(category);
	}
	
	@Transactional(readOnly=true)
	public Category findByCategoryId(Long categoryId){
		return categoryRepository.findOne(categoryId);
	}
	
	@Transactional(readOnly=true)
	public Category findByCategoryName(String categoryName){
		return categoryRepository.findOneByCategoryName(categoryName);
	}
	
	@Transactional
	public void delete(Long categoryId){
		categoryRepository.delete(categoryId);
	}
	
	@Transactional(readOnly=true)
	public List<Category> getAllCategories(){
		return categoryRepository.findAllByOrderByYearsFromAsc();
	}
	
	@Transactional(readOnly=true)
	public ExerciseType findByExerciseTypeId(Long exerciseTypeId){
		return exerciseTypeRepository.findOne(exerciseTypeId);
	}
	
	@Transactional(readOnly=true)
	public ExerciseType findByExerciseTypeName(String exerciseTypeName){
		return exerciseTypeRepository.findOneByExerciseTypeName(exerciseTypeName);
	}
	
	@Transactional(readOnly=true)
	public List<ExerciseType> getAllExerciseTypes(){
		return exerciseTypeRepository.findAllByOrderByExerciseTypeNameAsc();
	}

	
}
