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
@Table(name = "articlevaloration")
public class ArticleValoration implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long articleValorationId;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;

    @Column(name="articleId")
	@NotNull
	private long articleId;
	
	@NotNull
	private int valorationValue;
	
	public ArticleValoration() {
	}

	public ArticleValoration(User user, long articleId, int valorationValue) {
		this.user = user;
		this.articleId = articleId;
		this.valorationValue = valorationValue;
	}

	public long getArticleValorationId() {
		return articleValorationId;
	}

	public void setArticleValorationId(long articleValorationId) {
		this.articleValorationId = articleValorationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public int getValorationValue() {
		return valorationValue;
	}

	public void setValorationValue(int valorationValue) {
		this.valorationValue = valorationValue;
	}
	
}
