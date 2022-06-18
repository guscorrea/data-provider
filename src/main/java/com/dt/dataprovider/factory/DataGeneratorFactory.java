package com.dt.dataprovider.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.service.ChokeValveDataService;
import com.dt.dataprovider.service.DataGeneratorService;

@Component
public class DataGeneratorFactory {

	private ChokeValveDataService chokeValveDataService;

	@Autowired
	public DataGeneratorFactory(ChokeValveDataService chokeValveDataService) {
		this.chokeValveDataService = chokeValveDataService;
	}

	public DataGeneratorService getDataGeneratorService(ComponentType componentType) {
		if (ComponentType.choke.equals(componentType)) {
			return chokeValveDataService;
		}
		return null;
	}

}
