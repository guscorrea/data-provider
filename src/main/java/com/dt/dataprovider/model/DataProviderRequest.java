package com.dt.dataprovider.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.model.enums.MeasurementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The data provider request contains message and component related information", required = true)
public class DataProviderRequest {

	@NotNull
	@Schema(description = "The component type of the virtual DT sensor message", required = true)
	private ComponentType componentType;

	@NotNull
	@Schema(description = "The virtual DT component unique identifier", required = true, example = "ccf9e52b-e2e4-45d8-8884-0721d3246a53")
	private UUID componentId;

	@NotNull
	@Schema(description = "The measurement type of the virtual DT sensor message", required = true)
	private MeasurementType measurementType;

	@NotNull
	@Positive
	@Schema(description = "The number of messages to be generated in a single request", required = true, minLength = 1, example = "100")
	private int number;

	@NotNull
	@PositiveOrZero
	@Schema(description = "The rate in which messages will be generated in a single request", required = true, minLength = 0, example = "10")
	private int rate;

	@Schema(description = "Custom property name when measurement type is custom")
	private String customPropertyName;

}
