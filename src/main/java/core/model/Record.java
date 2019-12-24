package core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class Record implements Serializable, IRecord {

	private static final long serialVersionUID = 2815537750519909761L;

	private Long id;

	private String modifiedBy;
	private Date modifiedDate;

	private Boolean deleted = false;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

}
