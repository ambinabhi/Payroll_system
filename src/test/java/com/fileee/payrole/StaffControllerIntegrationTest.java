package com.fileee.payrole;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fileee.payrole.controllers.StaffMemberController;
import com.fileee.payrole.exception.StaffNotFoundException;
import com.fileee.payrole.repository.StaffRepository;
import com.fileee.payrole.services.MapperService;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.services.WorklogService;

@RunWith(SpringRunner.class)
@WebMvcTest(StaffMemberController.class)
class StaffControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StaffService staffService;
	@MockBean
	private MapperService mapperService;
	@MockBean
	private StaffRepository staffRepository;
	@MockBean
	private WorklogService worklogService;
	@MockBean
	private StaffNotFoundException resourceNotFoundException;
	
	@Test
	public void getAllStaffAlphabeticallyAPI() throws Exception 
	{
	  mvc.perform(MockMvcRequestBuilders
	      .get("/staff/list")
	      .accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk());
	}
	
	@Test
	public void getStaffByIdAPI() throws Exception 
	{
	  mvc.perform(MockMvcRequestBuilders
	      .get("/staff/view/{id}", 1)
	      .accept(MediaType.APPLICATION_JSON_VALUE))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

}
