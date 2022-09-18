package com.dt.dataprovider;

import java.io.PrintWriter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springdoc.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SwaggerITest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldGenerateSwaggerJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String response = mvc.perform(MockMvcRequestBuilders.get(Constants.DEFAULT_API_DOCS_URL))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value())).andReturn().getResponse().getContentAsString();
		String updatedResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapper.readValue(response, Object.class));
		PrintWriter writer = new PrintWriter("docs/swagger.json");
		writer.print(updatedResponse);
		writer.close();
	}

}
