package com.dt.dataprovider.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.service.AnmDataService;
import com.dt.dataprovider.service.ChokeValveDataService;
import com.dt.dataprovider.service.DataGeneratorService;
import com.dt.dataprovider.service.TubingDataService;

@Component
public class DataGeneratorFactory {

	private final ChokeValveDataService chokeValveDataService;

	private final AnmDataService anmDataService;

	private final TubingDataService tubingDataService;

	@Autowired
	public DataGeneratorFactory(ChokeValveDataService chokeValveDataService, AnmDataService anmDataService, TubingDataService tubingDataService) {
		this.chokeValveDataService = chokeValveDataService;
		this.anmDataService = anmDataService;
		this.tubingDataService = tubingDataService;
	}

	public DataGeneratorService getDataGeneratorService(ComponentType componentType) {

		switch (componentType) {
		case choke:
			return chokeValveDataService;
		case anm:
			return anmDataService;
		case tubing:
			return tubingDataService;
		}

		return null;
	}

}
