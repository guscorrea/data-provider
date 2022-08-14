package com.dt.dataprovider.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dt.dataprovider.factory.DataGeneratorFactory;
import com.dt.dataprovider.model.DataProviderRequest;
import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.model.enums.MeasurementType;
import com.dt.dataprovider.service.DataGeneratorService;

@RestController
public class DataProviderController {

	private final DataGeneratorFactory dataGeneratorFactory;

	@Autowired
	public DataProviderController(DataGeneratorFactory dataGeneratorFactory) {
		this.dataGeneratorFactory = dataGeneratorFactory;
	}

	@PostMapping("/v1/send-message")
	public ResponseEntity<Void> publishMessage(@RequestBody @Valid DataProviderRequest dataProviderRequest) {
		DataGeneratorService dataGenerator = dataGeneratorFactory.getDataGeneratorService(dataProviderRequest.getComponentType());
		dataGenerator.generateData(dataProviderRequest.getComponentId().toString(), dataProviderRequest.getMeasurementType(),
				dataProviderRequest.getNumber(), dataProviderRequest.getRate(), dataProviderRequest.getCustomPropertyName());
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/v2/send-message")
	public ResponseEntity<Void> publish(
			@RequestParam ComponentType componentType,
			@RequestParam String componentId,
			@RequestParam MeasurementType measurementType,
			@RequestParam(defaultValue = "1") int number,
			@RequestParam(defaultValue = "1") int rate,
			@RequestParam(required = false) String customPropertyName) {
		DataGeneratorService dataGenerator = dataGeneratorFactory.getDataGeneratorService(componentType);
		dataGenerator.generateData(componentId, measurementType, number, rate, customPropertyName);
		return ResponseEntity.accepted().build();
	}

}
