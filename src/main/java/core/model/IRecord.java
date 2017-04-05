package core.model;

import java.io.Serializable;
import java.util.Date;

public interface IRecord extends Serializable {
	
	Long getId();
	void setId(Long id);
	
	String getModifiedBy();
	void setModifiedBy(String modifiedBy);
	
	Date getModifiedDate();
	void setModifiedDate(Date modifiedDate);

	String getDisplayString();
	
}
