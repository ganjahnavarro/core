package core.dto;

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

	private String birthDate;
	private String address;
	private String contactNo;
	private String email;

	private String userName;
	private Boolean active = true;

	private String password;
	private String passwordConfirmation;

	private byte[] image;
	private String imageFileName;

	private String confirmationToken;

}
