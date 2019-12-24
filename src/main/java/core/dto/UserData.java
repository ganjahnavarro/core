package core.dto;

import java.util.Date;

import core.enums.Gender;
import core.enums.UserType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserData extends RecordData {

	private UserType type = UserType.DEFAULT;
	private Gender gender = Gender.MALE;

	private String lastName;
	private String firstName;
	private String middleName;

	private Date birthDate;
	private String address;
	private String contactNo;
	private String email;

	private String userName;
	private String password;
	private Boolean active = true;

	private byte[] image;
	private String imageFileName;

	private String confirmationToken;

}
