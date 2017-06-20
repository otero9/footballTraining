package footballtraining.app.controller.exercise;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import footballtraining.app.model.comment.Comment;
import footballtraining.app.model.comment.CommentRepository;
import footballtraining.app.model.exercise.Category;
import footballtraining.app.model.exercise.Exercise;
import footballtraining.app.model.exercise.ExerciseService;
import footballtraining.app.model.exercise.ExerciseType;
import footballtraining.app.model.exercise.ExerciseValoration;
import footballtraining.app.model.exercise.ExerciseValorationRepository;
import footballtraining.app.model.user.User;
import footballtraining.app.model.user.UserService;
import footballtraining.app.util.PageWrapper;

/**
 * The Class ExerciseController.
 */
@Controller
public class ExerciseController {

	private static final String EXERCISE_PAGE = "exercise/exercise";
	private static final String ERROR_PAGE = "error/general";
	private String imageData = null;
	
	@Autowired
	private ModifyExerciseFormValidator modifyExerciseFormValidator;

	@Autowired
	private ExerciseValorationRepository exerciseValorationRepository;
	
	@Autowired
	private ExerciseService exerciseService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private CommentRepository commentRepository;

	@RequestMapping(value = "/exercise/{exerciseId}", method = RequestMethod.GET)
	public String exercise(Principal principal, @PathVariable Long exerciseId, Model model) {
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		if (exerciseId != null) {
			Exercise exercise = exerciseService.findById(exerciseId);
			if (exercise != null) {
				model.addAttribute("exercise", exercise);
				if (exercise.getGraphic() != null) {
					if (exercise.getGraphic().getGraphicData() != null) {
						model.addAttribute("graphicjson", exercise.getGraphic().getGraphicData());
					} else {
						model.addAttribute("graphicjson", null);
					}
				} else {
					model.addAttribute("graphicjson", null);
				}
				model.addAttribute(new CreateCommentForm());
				List<Comment> comments = commentRepository.findByExerciseIdOrderByDateDesc(exerciseId);
				model.addAttribute("comments", comments);
				if ((user != null) && (user.getUserName()!=exercise.getUser().getUserName())) { 
					ExerciseValoration exerciseValoration = exerciseValorationRepository.findByUserAndExerciseId(user, exerciseId);
					if (exerciseValoration!=null) {
						model.addAttribute("notVoted", false);
						model.addAttribute("voted", true);
					} else {
						model.addAttribute("notVoted", true);
						model.addAttribute("voted", false);
					}
				}else {
					model.addAttribute("notVoted", false);
					model.addAttribute("voted", false);
				}
				return EXERCISE_PAGE;
			} else {
				String errorMessage = "Exercise doesn´t exists";
				model.addAttribute("errorMessage", errorMessage);
				return "error/general";
			}
		}
		String errorMessage = "Not authorized page";
		model.addAttribute("errorMessage", errorMessage);
		return "error/general";
	}

	@RequestMapping(value = "/exercise/allExercises", method = RequestMethod.GET)
	public String allExercise(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = false, name = "keywords") String keywords,
			@RequestParam(required = false, name = "selectedCategory") String selectedCategory,
			@RequestParam(required = false, name = "selectedExerciseType") String selectedExerciseType) {

		if (principal != null) {

			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		List<Category> categories = exerciseService.getAllCategories();
		model.addAttribute("categories", categories);
		List<ExerciseType> exerciseTypes = exerciseService.getAllExerciseTypes();
		model.addAttribute("exerciseTypes", exerciseTypes);
		Page<Exercise> exercisePage;
		PageWrapper<Exercise> page;
		pageable = new PageRequest(pageable.getPageNumber(), 10);
		try {

			if ((keywords != null) && (!keywords.isEmpty())) {
				model.addAttribute("keywords", keywords);
			} else {
				model.addAttribute("keywords", "");
			}

			if ((selectedCategory != null) && (!selectedCategory.isEmpty())) {
				model.addAttribute("selectedCategory", selectedCategory);
			} else {
				model.addAttribute("selectedCategory", "");
			}

			if ((selectedExerciseType != null) && (!selectedExerciseType.isEmpty())) {
				model.addAttribute("selectedExerciseType", selectedExerciseType);
			} else {
				model.addAttribute("selectedExerciseType", "");
			}

			exercisePage = exerciseService.findAllExercise(pageable, keywords, selectedCategory, selectedExerciseType);
			page = new PageWrapper<>(exercisePage, "/exercise/allExercises");

			model.addAttribute("exercises", page.getContent());
			model.addAttribute("page", page);
			return "exercise/allExercises";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
	}

	@RequestMapping(value = "/exercise/allExercises", method = RequestMethod.POST)
	public String allExerciseFilter(Principal principal, Model model, Pageable pageable,
			@RequestParam(required = true, name = "keywords") String keywords,
			@RequestParam(required = true, name = "selectedCategory") String selectedCategory,
			@RequestParam(required = true, name = "selectedExerciseType") String selectedExerciseType) {

		if (principal != null) {

			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		List<Category> categories = exerciseService.getAllCategories();
		model.addAttribute("categories", categories);
		List<ExerciseType> exerciseTypes = exerciseService.getAllExerciseTypes();
		model.addAttribute("exerciseTypes", exerciseTypes);
		Page<Exercise> exercisePage;
		PageWrapper<Exercise> page;
		pageable = new PageRequest(pageable.getPageNumber(), 10);
		try {
			if ((keywords != null) && (!keywords.isEmpty())) {
				model.addAttribute("keywords", keywords);
			} else {
				model.addAttribute("keywords", "");
			}

			if ((selectedCategory != null) && (!selectedCategory.isEmpty())) {
				model.addAttribute("selectedCategory", selectedCategory);
			} else {
				model.addAttribute("selectedCategory", "");
			}

			if ((selectedExerciseType != null) && (!selectedExerciseType.isEmpty())) {
				model.addAttribute("selectedExerciseType", selectedExerciseType);
			} else {
				model.addAttribute("selectedExerciseType", "");
			}
			exercisePage = exerciseService.findAllExercise(pageable, keywords, selectedCategory, selectedExerciseType);
			page = new PageWrapper<>(exercisePage, "/exercise/allExercises");
			model.addAttribute("exercises", page.getContent());
			model.addAttribute("page", page);
			return "exercise/allExercises";
		} catch (Exception e) {
			String errorMessage = "Not authorized page";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}

	}

	

	@RequestMapping(value = "exercise/deleteComment/{commentId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String deleteComment(@PathVariable String commentId, Principal principal, Model model) {
		Comment comment = commentRepository.findOne(Long.valueOf(commentId));
		Exercise exercise = exerciseService.findById(comment.getExerciseId());
		try {
			commentRepository.delete(comment.getCommentId());
		} catch (Exception e) {
			String errorMessage = "Selected comment to delete is not existing";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}

		return "redirect:/exercise/" + exercise.getExerciseId();
	}

	@RequestMapping(value = "/exercise/downloadPDF/{exerciseId}", method = RequestMethod.POST)
	public void downloadPDF(@PathVariable String exerciseId, HttpServletRequest request, HttpServletResponse response) {

		if (exerciseId != null) {
			Exercise exercise = exerciseService.findById(Long.valueOf(exerciseId));
			Document document = new Document();
			try {
				String fileName = "exercise.pdf";
				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "attachment; filename=" + fileName);
				//String userimage = request.getParameter("imageData");
				//byte[] decodedString = Base64.getDecoder().decode(new String(userimage).getBytes("UTF-8"));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PdfWriter writer = PdfWriter.getInstance(document, baos);
				document.open();
				
				Font boldFontTitle = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD);

				Paragraph title = new Paragraph(exercise.getExerciseTitle(), boldFontTitle);
				title.setAlignment(Element.ALIGN_CENTER);
				title.setSpacingAfter(40f);
				title.setSpacingBefore(150f);
				document.add(title);

				PdfPTable table = new PdfPTable(1); // 1 columns.
				table.setWidthPercentage(100); // Width 100%
				table.setSpacingBefore(50f); // Space before table
				table.setSpacingAfter(30f); // Space after table
				Font boldFont = new Font(Font.FontFamily.UNDEFINED, 14, Font.BOLD);
				PdfPCell cell1 = new PdfPCell(new Paragraph("Description", boldFont));
				cell1.setPaddingLeft(10);
				cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell1);
				PdfPCell cell2 = new PdfPCell(new Paragraph(exercise.getExerciseDescription()));
				cell2.setPaddingLeft(10);
				table.addCell(cell2);
				document.add(table);

				PdfPTable table2 = new PdfPTable(1); // 1 columns.
				table2.setWidthPercentage(100); // Width 100%
				table2.setSpacingBefore(30f); // Space before table
				table2.setSpacingAfter(30f); // Space after table
				PdfPCell cell3 = new PdfPCell(new Paragraph("Objectives", boldFont));
				cell3.setPaddingLeft(10);
				cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table2.addCell(cell3);
				PdfPCell cell4 = new PdfPCell(new Paragraph(exercise.getExerciseObjective()));
				cell4.setPaddingLeft(10);
				table2.addCell(cell4);
				document.add(table2);

				PdfPTable table3 = new PdfPTable(2); // 2 columns.
				table3.setWidthPercentage(100);
				table3.setSpacingBefore(30f); // Space before table
				table3.setSpacingAfter(30f); // Space after table
				PdfPCell cell5 = new PdfPCell(new Paragraph("Loads", boldFont));
				cell5.setPaddingLeft(10);
				cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table3.addCell(cell5);
				PdfPCell cell7 = new PdfPCell(new Paragraph("Breaks", boldFont));
				cell7.setPaddingLeft(10);
				cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table3.addCell(cell7);
				PdfPCell cell6 = new PdfPCell(new Paragraph(exercise.getLoads()));
				cell6.setPaddingLeft(10);
				table3.addCell(cell6);
				PdfPCell cell8 = new PdfPCell(new Paragraph(exercise.getBreaks()));
				cell8.setPaddingLeft(10);
				table3.addCell(cell8);

				document.add(table3);
				//Image image = Image.getInstance(decodedString);
				//document.add(image);
				
				document.close();

				baos.toByteArray();
				OutputStream os = response.getOutputStream();
				baos.writeTo(os);
				os.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@RequestMapping(value = "/exercise/downloadImage", method = RequestMethod.POST)
	public void downloadImage(HttpServletRequest request, HttpServletResponse response) {
		this.imageData = request.getHeader("imageData");
	}

	@RequestMapping(value = "/exercise/userExercises/{userName}", method = RequestMethod.GET)
	public String userExercises(Principal principal, Model model, Pageable pageable, @PathVariable String userName) {

		if (principal != null) {
			User user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
		}
		User userExercise = null;
		try {
			userExercise = userService.findByUserName(userName);
		} catch (Exception e) {
			String errorMessage = "User doesn't exists";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
		if (userExercise != null) {
			model.addAttribute("userExercise", userExercise);
			Page<Exercise> exercisePage;
			PageWrapper<Exercise> page;
			pageable = new PageRequest(pageable.getPageNumber(), 10);
			try {
				exercisePage = exerciseService.findUserExercises(userExercise, pageable);
				page = new PageWrapper<>(exercisePage, "/exercise/userExercises/" + userName);

				model.addAttribute("exercises", page.getContent());
				model.addAttribute("page", page);
				return "exercise/userExercises";
			} catch (Exception e) {
				String errorMessage = "Not authorized page";
				model.addAttribute("errorMessage", errorMessage);
				return "error/general";
			}
		} else {
			String errorMessage = "User doesn't exists";
			model.addAttribute("errorMessage", errorMessage);
			return "error/general";
		}
	}
	
	@RequestMapping(value = "/valorateExercise/{exerciseId}/{valoration}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String valorateExercise(Principal principal, @PathVariable Long exerciseId, @PathVariable Integer valoration, Model model) {
		User user = null;
		if ((principal != null)&&(valoration!=null)) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			
			Exercise exercise = exerciseService.findById(exerciseId);
			
			if ((user!=null)&&(exercise!=null)) {
				try {
					ExerciseValoration exerciseValoration = new ExerciseValoration(user, exerciseId, valoration);
					exerciseValorationRepository.save(exerciseValoration);
					Integer v;
					if (exercise.getValorationExercise()>0) {
						v = (int) (exercise.getValorationExercise() * exercise.getValorationCountExercise()) + valoration;
					} else {
						v = valoration;
					}
					 
					Float newValoration = (float) (v  / (exercise.getValorationCountExercise() + 1));
					exercise.setValorationExercise(newValoration);
					exercise.setValorationCountExercise(exercise.getValorationCountExercise()+1);
					exerciseService.save(exercise);
				} catch (Exception e) {
					String errorMessage = "Error occurred to vote this exercise";
					model.addAttribute("errorMessage", errorMessage);
					return ERROR_PAGE;
				}
			} else  {
				String errorMessage = "The exercise does not exists";
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}

			return "redirect:/exercise/"+exerciseId;
		} else {
			String errorMessage = "You don't have permission";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}
		
	}
	
	@RequestMapping(value = "/createComment/{exerciseId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String createComment(@PathVariable String exerciseId, @Valid @ModelAttribute CreateCommentForm createCommentForm, Errors errors, RedirectAttributes ra,
			Principal principal, Model model) {
		User user = userService.findByUserName(principal.getName());
		model.addAttribute("user", user);
		createCommentForm.setUser(user);
		createCommentForm.setExerciseId(Long.valueOf(exerciseId));
		Comment comment = createCommentForm.createComment();
		commentRepository.save(comment);
		Exercise exercise = exerciseService.findById(Long.valueOf(exerciseId));
		model.addAttribute("exercise", exercise);
		List<Comment> comments = commentRepository.findByExerciseIdOrderByDateDesc(exercise.getExerciseId());
		model.addAttribute("comments", comments);
		
		return "redirect:/exercise/"+exerciseId;	
	}
	
	@RequestMapping(value = "/exercise/deleteExercise/{exerciseId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String deleteExercise(Principal principal, @PathVariable Long exerciseId, Model model) {
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			
			Exercise exercise = exerciseService.findById(exerciseId);
			
			if ((user!=null)&&(exercise!=null)) {
				try {
					exerciseService.delete(exercise);
					return "redirect:/user/"+user.getUserId()+"/details";
				} catch (Exception e) {
					String errorMessage = "Error occurred to remove this exercise";
					model.addAttribute("errorMessage", errorMessage);
					return ERROR_PAGE;
				}
			} else  {
				String errorMessage = "The exercise does not exists";
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}
		} else {
			String errorMessage = "You don't have permission";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}
	}
	
	@RequestMapping(value = "/exercise/deleteGraphic/{exerciseId}", method = RequestMethod.POST)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String deleteGraphic(Principal principal, @PathVariable Long exerciseId, Model model) {
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			
			Exercise exercise = exerciseService.findById(exerciseId);
			
			if ((user!=null)&&(exercise!=null)) {
				try {
					exerciseService.removeGraphicToExercise(exercise);
					return "redirect:/exercise/"+exercise.getExerciseId();
				} catch (Exception e) {
					String errorMessage = "Error occurred to remove this exercise";
					model.addAttribute("errorMessage", errorMessage);
					return ERROR_PAGE;
				}
			} else  {
				String errorMessage = "The exercise does not exists";
				model.addAttribute("errorMessage", errorMessage);
				return ERROR_PAGE;
			}
		} else {
			String errorMessage = "You don't have permission";
			model.addAttribute("errorMessage", errorMessage);
			return ERROR_PAGE;
		}
	}

	@RequestMapping(value = "exercise/modifyExercise/{id}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String modifyExercise(Principal principal, @PathVariable Long id, Model model) throws IOException {
		
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			
			Exercise exerciseActual = exerciseService.findById(id);
			
			if ((user!=null)&&(exerciseActual!=null)) {
				ModifyExerciseDTO exercise = new ModifyExerciseDTO();
				exercise.setExerciseId(exerciseActual.getExerciseId());
				exercise.setExerciseTitle(exerciseActual.getExerciseTitle());
				exercise.setExerciseDescription(exerciseActual.getExerciseDescription());
				exercise.setBreaks(exerciseActual.getBreaks());
				exercise.setLoads(exerciseActual.getLoads());
				exercise.setExerciseObjective(exerciseActual.getExerciseObjective());
				exercise.setVideoURL(exerciseActual.getVideoURL());
				model.addAttribute("exercise", exercise);
				model.addAttribute(new ModifyExerciseForm());
				return "exercise/modifyExercise";
			}
		}
		String errorMessage = "Exercise not exists";
		model.addAttribute("errorMessage", errorMessage);
		return ERROR_PAGE;
	}	
	
	
	@RequestMapping(value = "exercise/modifyExercise/{id}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public String modifyExercise(@Valid @ModelAttribute ModifyExerciseForm modifyExerciseForm, Principal principal, @PathVariable Long id, Model model, BindingResult bindingResult, Errors errors, RedirectAttributes ra) throws IOException {
		
		User user = null;
		if (principal != null) {
			user = userService.findByUserName(principal.getName());
			model.addAttribute("user", user);
			
			modifyExerciseFormValidator.validate(modifyExerciseForm, errors);
			if (errors.hasErrors()) {
				return "exercise/modifyExercise";
			}
			
			Exercise exercise = exerciseService.findById(id);

			if ((user!=null)&&(exercise!=null)) {
				try {
					List<Category> categories = new ArrayList<Category>();
					List<ExerciseType> exerciseTypes = new ArrayList<ExerciseType>();
					ExerciseDTO exerciseDTO = modifyExerciseForm.createExercise();
					if (!exerciseDTO.getExerciseTitle().isEmpty())
						exercise.setExerciseTitle(exerciseDTO.getExerciseTitle());
					if (!exerciseDTO.getExerciseDescription().isEmpty())
						exercise.setExerciseDescription(exerciseDTO.getExerciseDescription());
					if (!exerciseDTO.getBreaks().isEmpty())
						exercise.setBreaks(exerciseDTO.getBreaks());
					if (!exerciseDTO.getLoads().isEmpty())
						exercise.setLoads(exerciseDTO.getLoads());
					if (!exerciseDTO.getExerciseObjective().isEmpty())
						exercise.setExerciseObjective(exerciseDTO.getExerciseObjective());
					if (!exerciseDTO.getVideoURL().isEmpty()) {
						String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
					    Pattern compiledPattern = Pattern.compile(pattern);
					    Matcher matcher = compiledPattern.matcher(exerciseDTO.getVideoURL());
					    String idVideo = null;
					    if(matcher.find()){
					    	idVideo = matcher.group();
					    }
						    
						if ((idVideo != null) && (!idVideo.isEmpty())) {
							exerciseDTO.setVideoURL("https://www.youtube.com/embed/"+id);
						}
						exercise.setVideoURL(exerciseDTO.getVideoURL());
					}
						
					for (String categoryName : exerciseDTO.getCategories()){
						categories.add(exerciseService.findByCategoryName(categoryName));
					}
					for (String exerciseTypeName : exerciseDTO.getExerciseTypes()){
						exerciseTypes.add(exerciseService.findByExerciseTypeName(exerciseTypeName));
					}
					if (!categories.isEmpty()){
						exercise.getCategories().clear();
						exercise.getCategories().addAll(categories);
					}
					if (!exerciseTypes.isEmpty()){
						exercise.getExerciseTypes().clear();
						exercise.getExerciseTypes().addAll(exerciseTypes);
					}
					exercise = exerciseService.update(exercise);
					return "redirect:/exercise/"+exercise.getExerciseId();
				} catch (Exception e) {
					String errorMessage="It is not possible to update the exercise";
					return ERROR_PAGE;
				}
			}
		}
		String errorMessage = "Exercise not exists";
		model.addAttribute("errorMessage", errorMessage);
		return ERROR_PAGE;
		
	}
		
}
