package com.dt.dataprovider.service;

import com.dt.dataprovider.model.enums.MeasurementType;

public interface DataGeneratorService {

	void generateData(String serviceName, MeasurementType measurementType, int number, int rate, String customPropertyName);

}
