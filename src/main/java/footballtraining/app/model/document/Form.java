package footballtraining.app.model.document;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import footballtraining.app.model.user.User;

@Entity
@Table(name = "form")
public class Form implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long formId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	@NotNull
	private String formTitle;
	
	@NotNull
	private String formDescription;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "documentId")
	private Document document;
	
	@NotNull
    @Column(name="date")
    private Date date;
	
	@NotNull
	private Float valorationForm;
	
	@NotNull
	private int valorationCountForm;
	
	public Form() {
	}

	public Form(User user, String formTitle, String formDescription, Document document, Float valorationForm, int valorationCountForm) {
		this.user = user;
		this.formTitle = formTitle;
		this.formDescription = formDescription;
		this.document = document;
		this.valorationForm = valorationForm;
		this.valorationCountForm = valorationCountForm;
		this.date = new Date();
	}

	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFormDescription() {
		return formDescription;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public Float getValorationForm() {
		return valorationForm;
	}

	public void setValorationForm(Float valorationForm) {
		this.valorationForm = valorationForm;
	}

	public int getValorationCountForm() {
		return valorationCountForm;
	}

	public void setValorationCountForm(int valorationCountForm) {
		this.valorationCountForm = valorationCountForm;
	}
	
}
