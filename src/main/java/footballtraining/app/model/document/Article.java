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
@Table(name = "article")
public class Article implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long articleId;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;
	
	@NotNull
	private String articleTitle;
	
	@NotNull
	private String articleDescription;
	
	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "documentId")
	private Document document;
	
	@NotNull
    @Column(name="date")
    private Date date;
	
	@NotNull
	private Float valorationArticle;
	
	@NotNull
	private int valorationCountArticle;
	
	public Article() {
	}

	public Article(User user, String articleTitle, String articleDescription, Document document, Float valorationArticle, int valorationCountArticle) {
		this.user = user;
		this.articleTitle = articleTitle;
		this.articleDescription = articleDescription;
		this.document = document;
		this.valorationArticle = valorationArticle;
		this.valorationCountArticle = valorationCountArticle;
		this.date = new Date();
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getArticleDescription() {
		return articleDescription;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}

	public Float getValorationArticle() {
		return valorationArticle;
	}

	public void setValorationArticle(Float valorationArticle) {
		this.valorationArticle = valorationArticle;
	}

	public int getValorationCountArticle() {
		return valorationCountArticle;
	}

	public void setValorationCountArticle(int valorationCountArticle) {
		this.valorationCountArticle = valorationCountArticle;
	}

}
