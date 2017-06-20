package footballtraining.app.model.exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	@Column(name = "categoryid")
	private long categoryId;

	@NotNull
	@Size(min = 2, max = 80)
	@Column(name = "categoryname")
	private String categoryName;

	@NotNull
	@Column(name = "yearsfrom")
	private int yearsFrom;

	@Column(name = "yearsuntil")
	private int yearsUntil;

	@NotNull
	@Column(name = "categorydescription")
	private String categoryDescription;

	public Category() {
	}

	public Category(String categoryName, int yearsFrom, int yearsUntil, String categoryDescription) {
		this.categoryName = categoryName;
		this.yearsFrom = yearsFrom;
		this.yearsUntil = yearsUntil;
		this.categoryDescription = categoryDescription;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getYearsFrom() {
		return yearsFrom;
	}

	public void setYearsFrom(int yearsFrom) {
		this.yearsFrom = yearsFrom;
	}

	public int getYearsUntil() {
		return yearsUntil;
	}

	public void setYearsUntil(int yearsUntil) {
		this.yearsUntil = yearsUntil;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

}