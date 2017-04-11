package core.dto.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import core.dto.UserData;
import core.model.User;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "modifiedDate", source = "modifiedDate", dateFormat = "MM/dd/yyyy HH:mm")
	@Mapping(target = "birthDate", source = "birthDate", dateFormat = "MM/dd/yyyy HH:mm")
	UserData toData(User user);
	
	@IterableMapping(dateFormat = "MM/dd/yyyy HH:mm")
	List<UserData> toData(List<User> users);
	
	@InheritInverseConfiguration
	User fromData(UserData userData);

}
