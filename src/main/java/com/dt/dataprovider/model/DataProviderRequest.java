package com.dt.dataprovider.model;

import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.dt.dataprovider.model.enums.ComponentType;
import com.dt.dataprovider.model.enums.MeasurementType;
import lombok.Data;

@Data
public class DataProviderRequest {

	@NotNull
	private ComponentType componentType;

	@NotNull
	private UUID componentId;

	@NotNull
	private MeasurementType measurementType;

	@NotNull
	@Positive
	private int number;

	@NotNull
	@PositiveOrZero
	private int rate;

	private String customPropertyName;

}
