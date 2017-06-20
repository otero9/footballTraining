package footballtraining.app.model.document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import footballtraining.app.model.user.User;

@Entity
@Table(name = "formvaloration")
public class FormValoration implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long formValorationId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
    @Column(name="formId")
	@NotNull
	private long formId;
	
	@NotNull
	private int valorationValue;
	
	public FormValoration() {
	}

	public FormValoration(User user, long formId, int valorationValue) {
		this.user = user;
		this.formId = formId;
		this.valorationValue = valorationValue;
	}

	public long getFormValorationId() {
		return formValorationId;
	}

	public void setFormValorationId(long formValorationId) {
		this.formValorationId = formValorationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public int getValorationValue() {
		return valorationValue;
	}

	public void setValorationValue(int valorationValue) {
		this.valorationValue = valorationValue;
	}
	
}
