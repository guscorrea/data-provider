package com.dt.dataprovider.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Data Provider")
public class DataProviderController {

	private final DataGeneratorFactory dataGeneratorFactory;

	@Autowired
	public DataProviderController(DataGeneratorFactory dataGeneratorFactory) {
		this.dataGeneratorFactory = dataGeneratorFactory;
	}

	@PostMapping("/v1/send-message")
	@Operation(summary = "Posts one or more messages to the proper MQTT broker",
			description = "Generates one or more messages to be posted in the Mosquitto broker topic. Parameters will determine what type of message"
					+ " it will be, the rate in which the messages will be posted, and to what component it belongs.",
			responses = { @ApiResponse(responseCode = "202", description = "Message request was accepted and will be processed asynchronously."),
					@ApiResponse(responseCode = "400", description = "The request failed validation.", content = {
							@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
									schema = @Schema(type = "string", example = "Field componentType: must not be null")) }),
					@ApiResponse(responseCode = "500", description = "Unexpected error occurred",
							content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	public ResponseEntity<Void> publishMessage(@RequestBody @Valid DataProviderRequest dataProviderRequest) {
		DataGeneratorService dataGenerator = dataGeneratorFactory.getDataGeneratorService(dataProviderRequest.getComponentType());
		dataGenerator.generateData(dataProviderRequest.getComponentId().toString(), dataProviderRequest.getMeasurementType(),
				dataProviderRequest.getNumber(), dataProviderRequest.getRate(), dataProviderRequest.getCustomPropertyName());
		return ResponseEntity.accepted().build();
	}

	@PostMapping("/v2/send-message")
	@Hidden
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
