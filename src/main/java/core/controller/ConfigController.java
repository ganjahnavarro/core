package core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.dto.ConfigData;
import core.dto.mapper.ConfigMapper;
import core.model.Config;
import core.service.ConfigService;

@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {

	@Autowired
	private ConfigService service;

	private ConfigMapper MAPPER = ConfigMapper.INSTANCE;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public List<ConfigData> list(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "pageOffset", required = false) Integer pageOffset,
			@RequestParam(value = "orderedBy", required = false) String orderedBy) {
		return MAPPER.toData(service.findFilteredItems(filter, pageSize, pageOffset, orderedBy));
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ConfigData create(@RequestBody ConfigData configData) {
		Config config = MAPPER.fromData(configData);
		return MAPPER.toData((Config) service.save(config));
	}

	@RequestMapping(value = "/", method = RequestMethod.PATCH)
	public ConfigData update(@RequestBody ConfigData configData) {
		Config config = MAPPER.fromData(configData);
		return MAPPER.toData((Config) service.update(config));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		service.deleteRecordById(id);
	}

}
