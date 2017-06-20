package footballtraining.app.model.document;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "document")
public class Document implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private long documentId;
	
	@NotNull
	private String documentName;
	
	@NotNull
	private String documentMine;
	
	@NotNull
	private byte[] documentData;
	
	@NotNull
	private Date documentCreated;
	
	public Document() {
	}

	public Document(String documentName, String documentMine, byte[] documentData, Date documentCreated) {
		this.documentName = documentName;
		this.documentMine = documentMine;
		this.documentData = documentData;
		this.documentCreated = documentCreated;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentMine() {
		return documentMine;
	}

	public void setDocumentMine(String documentMine) {
		this.documentMine = documentMine;
	}

	public byte[] getDocumentData() {
		return documentData;
	}

	public void setDocumentData(byte[] documentData) {
		this.documentData = documentData;
	}

	public Date getDocumentCreated() {
		return documentCreated;
	}

	public void setDocumentCreated(Date documentCreated) {
		this.documentCreated = documentCreated;
	}
	
}
