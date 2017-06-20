package footballtraining.app.controller.document;

import org.hibernate.validator.constraints.NotBlank;

import footballtraining.app.model.document.Article;
import footballtraining.app.model.document.Form;

public class UploadDocumentForm {

	private static final String NOT_BLANK_MESSAGE = "Rellene el campo";
	
	@NotBlank(message = UploadDocumentForm.NOT_BLANK_MESSAGE)
	private String documentTitle;
	
	@NotBlank(message = UploadDocumentForm.NOT_BLANK_MESSAGE)
	private String documentDescription;
	
	@NotBlank(message = UploadDocumentForm.NOT_BLANK_MESSAGE)
	private String documentType;
	

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	
	/**
	 * Función que devuelve un video con los campos correctos a partir de los datos introducidos en un formulario.
	 *
	 * @return El video con los datos introducidos en el formulario
	 */
	public Form uploadForm() {
		return new Form(null, documentTitle, documentDescription, null, Float.valueOf(0), 0);
	}
	
	public Article uploadArticle() {
		return new Article(null, documentTitle, documentDescription, null, Float.valueOf(0), 0);
	}
	
}
