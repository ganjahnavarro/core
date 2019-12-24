package core.dto.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import core.dto.ConfigData;
import core.model.Config;

@Mapper
public interface ConfigMapper {
	
	ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);

	@Mapping(target = "modifiedDate", source = "modifiedDate", dateFormat = "MM/dd/yyyy HH:mm")
	ConfigData toData(Config config);
	
	@IterableMapping(dateFormat = "MM/dd/yyyy HH:mm")
	List<ConfigData> toData(List<Config> configs);
	
	@InheritInverseConfiguration
	Config fromData(ConfigData configData);

}
