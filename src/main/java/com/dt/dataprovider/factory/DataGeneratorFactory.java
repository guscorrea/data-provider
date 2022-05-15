package com.dt.dataprovider.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.dataprovider.model.enums.ServiceType;
import com.dt.dataprovider.service.ChokeValveDataService;
import com.dt.dataprovider.service.DataGeneratorService;

@Component
public class DataGeneratorFactory {

	private ChokeValveDataService chokeValveDataService;

	@Autowired
	public DataGeneratorFactory(ChokeValveDataService chokeValveDataService) {
		this.chokeValveDataService = chokeValveDataService;
	}

	public DataGeneratorService getDataGeneratorService(ServiceType serviceType) {
		if (ServiceType.choke.equals(serviceType)) {
			return chokeValveDataService;
		}
		return null;
	}

}
