package com.dt.dataprovider.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.service.AnmDataService;
import com.dt.dataprovider.service.ChokeValveDataService;
import com.dt.dataprovider.service.DataGeneratorService;

@Component
public class DataGeneratorFactory {

	private final ChokeValveDataService chokeValveDataService;

	private final AnmDataService anmDataService;

	@Autowired
	public DataGeneratorFactory(ChokeValveDataService chokeValveDataService, AnmDataService anmDataService) {
		this.chokeValveDataService = chokeValveDataService;
		this.anmDataService = anmDataService;
	}

	public DataGeneratorService getDataGeneratorService(ComponentType componentType) {
		if (ComponentType.choke.equals(componentType)) {
			return chokeValveDataService;
		}
		else if (ComponentType.anm.equals(componentType)) {
			return anmDataService;
		}
		return null;
	}

}
