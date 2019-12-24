package core.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;

@Audited
@Entity(name = Config.ENTITY_NAME)
public class Config extends Record {

	private static final long serialVersionUID = -6260924369830716245L;
	public static final String ENTITY_NAME = "config";

	private String name;
	private String value;

	@NotBlank(message = "Name is required")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	@Transient
	public String getDisplayString() {
		return getName();
	}

}
