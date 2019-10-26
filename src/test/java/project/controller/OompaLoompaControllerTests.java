package project.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.POJO.OompaLoompa;
import project.service.OompaLoompaService;

import org.testcontainers.containers.GenericContainer;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OompaLoompaControllerTests {

	private static final String MONGO_CONTAINER = "mongo:3.1.5";
    private static final Integer MONGO_PORT = 27017;
    private static final int CONFIG_SERVICE_PORT = 8080;

    @ClassRule
    public static GenericContainer mongo = new GenericContainer(MONGO_CONTAINER).withExposedPorts(MONGO_PORT);
    
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private OompaLoompaService oompaLoompaService;
    @BeforeClass
    public static void before() {
        System.setProperty("spring.data.mongodb.uri", "mongodb://" + mongo.getContainerIpAddress() + ":" + mongo.getMappedPort(MONGO_PORT));
    }
	@Test
	public void ListAccountsTest() throws Exception {

        OompaLoompa insertedOl = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
        insertedOl.setId("1");
        Page<OompaLoompa> page = new PageImpl<>(Arrays.asList(insertedOl));
        
		Mockito.doReturn(page).when(oompaLoompaService).getAllOompaLoompas(Mockito.any());   
		
		mockMvc.perform(get("/oompaLoompas")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", hasSize(1)));
		
	}
	
	@Test
	public void testPostOompaLoompa() throws Exception {
		
		OompaLoompa ol = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ol);
        
        OompaLoompa insertedOl = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
        insertedOl.setId("1");
        
        Mockito.doReturn(insertedOl).when(oompaLoompaService).insertOompaLoompa(Mockito.any());   
        
		mockMvc.perform(post("/oompaLoompas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated())
			.andExpect(redirectedUrlPattern("http://*/oompaLoompas/1"));
			
	}
	
	
	@Test
	public void testGetOompaLoompaById() throws Exception {
        OompaLoompa insertedOl = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
        insertedOl.setId("1");
        
        Mockito.doReturn(Optional.of(insertedOl)).when(oompaLoompaService).getOompaLoompaById("1");  
		mockMvc.perform(get("/oompaLoompas/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is("1")))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testGetOompaLoompaByIdNotFound() throws Exception {

        Mockito.doReturn(Optional.empty()).when(oompaLoompaService).getOompaLoompaById("1");  
		mockMvc.perform(get("/oompaLoompas/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testUpdateOompaLoompaById() throws Exception {
		
		OompaLoompa ol = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ol);
        OompaLoompa insertedOl = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
        insertedOl.setId("1");
        
        Mockito.when(oompaLoompaService.saveOompaLoompa(Mockito.any(), Mockito.eq("1"))).thenReturn(Optional.of(insertedOl));  
		mockMvc.perform(put("/oompaLoompas/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void testUpdateOompaLoompaByIdNotFound() throws Exception {
		
		OompaLoompa ol = new OompaLoompa("Robert", 20,"Carpenter",130,40.0 ,  "He likes helping his teammates");
		ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(ol);
        
        Mockito.when(oompaLoompaService.saveOompaLoompa(Mockito.any(), Mockito.eq("1"))).thenReturn(Optional.empty());  
		mockMvc.perform(put("/oompaLoompas/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isNotFound());
	}
}
