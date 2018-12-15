package com.acme.thing;

import com.acme.thing.model.Thing;
import com.acme.thing.model.ThingRepository;
import com.acme.thing.support.LocalThingReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ThingApplication.class, LocalThingReader.class})
@WebAppConfiguration
public class ThingApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private ThingRepository repository;

	@Autowired
	private LocalThingReader thingReader;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		Thing thing = new Thing();
		thing.setName("Test API");
		thing.setDescription("A public API for testing");
		thing.setAuth("apikey");
		thing.setHttps(false);
		thing.setCors("yes");
		thing.setLink("http://somewhere.org");
		thing.setCategory("Test");
		List<Thing> things = new ArrayList<>();
		things.add(thing);
		Page<Thing> page = new PageImpl<>(things);
		given(repository.findAll(any(Pageable.class))).willReturn(page);
		given(repository.count()).willReturn(1L);
	}

	@Test
	public void controllerShouldReturnOneThing() throws Exception {
		MvcResult result = mockMvc.perform(get("/api/v1/things").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(status,200);
		ObjectMapper mapper = new ObjectMapper();
		List<Thing> things = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Thing>>(){});
		assertEquals(things.size(), 1);
	}

	@Test
	public void localReaderShouldReadFiveHundredNinetyFiveThings() throws IOException {
		List<Thing> things = thingReader.readThings();
		assertEquals(things.size(), 595);
	}
}

