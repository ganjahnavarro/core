package core.model;

import java.io.Serializable;
import java.util.Date;

public interface IRecord extends Serializable {
	
	Long getId();
	void setId(Long id);
	
	String getCreatedBy();
	void setCreatedBy(String createdBy);
	
	Date getCreatedDate();
	void setCreatedDate(Date createdDate);
	
	String getModifiedBy();
	void setModifiedBy(String modifiedBy);
	
	Date getModifiedDate();
	void setModifiedDate(Date modifiedDate);
	
	Boolean getDeleted();
	void setDeleted(Boolean deleted);

	String getDisplayString();
	
}
