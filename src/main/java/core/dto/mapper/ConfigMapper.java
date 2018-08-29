package core.dto.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import core.dto.ConfigData;
import core.model.Config;

@Mapper
public interface ConfigMapper {
	
	ConfigMapper INSTANCE = Mappers.getMapper(ConfigMapper.class);

	ConfigData toData(Config config);
	
	List<ConfigData> toData(List<Config> configs);
	
	@InheritInverseConfiguration
	Config fromData(ConfigData configData);

}
