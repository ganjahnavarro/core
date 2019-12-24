package core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import core.enums.Gender;
import core.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = User.ENTITY_NAME)
public class User extends Record {
	
	public static final String ENTITY_NAME = "applicationUser";
	private static final long serialVersionUID = 8438443391159451383L;

	@NotNull(message = "Type is required.")
	@Enumerated(EnumType.STRING)
	private UserType type = UserType.DEFAULT;
	
	@Enumerated(EnumType.STRING)
	private Gender gender = Gender.MALE;
	
	@NotBlank(message = "Last name is required.")
	private String lastName;
	
	@NotBlank(message = "First name is required.")
	private String firstName;
	
	private String middleName;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date birthDate;
	
	private String address;
	private String contactNo;
	private String email;
	
	@Column(unique = true)
	@NotBlank(message = "Username is required.")
	private String userName;
	
	@NotBlank(message = "Password is required.")
	private String password;
	
	private Boolean active = true;
	
	@Lob
	private byte[] image;
	
	private String imageFileName;
	
	private String confirmationToken;
	
	
	@Transient
	@Override
	public String getDisplayString() {
		return (lastName != null ? lastName + ", " : "")
				+ (firstName != null ? firstName + " " : "")
				+ (middleName != null ? middleName : "");
	}

}
