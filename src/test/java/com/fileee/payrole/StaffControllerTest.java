package com.fileee.payrole;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.controllers.StaffMemberController;
import com.fileee.payrole.exception.ResourceNotFoundException;
import com.fileee.payrole.repository.StaffRepository;
import com.fileee.payrole.services.PatchService;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.services.WorklogService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StaffMemberController.class)
class StaffControllerTest {

	@Autowired
	StaffMemberController staffMemberController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StaffService staffService;
	@MockBean
	private PatchService patchService;
	@MockBean
	private StaffRepository staffRepository;
	@MockBean
	private WorklogService worklogService;
	@MockBean
	private ResourceNotFoundException resourceNotFoundException;
	
	@Test
	public void save_StaffTest_OK() throws Exception {
		// mock the data to be saved
		Staff staff = new Staff();
		staff.setStaffId(15);
		staff.setName("Kate Hudson");
		staff.setIsHourly(false);
		staff.setEmail("kathyson1985@gmail.com");
		staff.setWage(2500);

		when(staffService.save(any(Staff.class))).thenReturn(staff);

		// creating a mock HTTP request to verify the result
		mockMvc.perform(MockMvcRequestBuilders.post("/staff").content(new ObjectMapper().writeValueAsString(staff))
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.staffId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Kate Hudson"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("kathyson1985@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.isHourly").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$.wage").value(2500));
	}
	
	@Test
	public void get_staffByIdTest_OK() throws Exception {

		// mock the data returned by user service
		Staff staff = new Staff();
		staff.setName("John Doe");
		staff.setIsHourly(true);
		staff.setEmail("john.doe@gmail.com");
		staff.setWage(20);

		Optional<Staff> staffMember = Optional.of(staff);
		when(staffService.findOneById(anyInt())).thenReturn(staffMember);

		// creating a mock HTTP request to verify the result
		mockMvc.perform(MockMvcRequestBuilders.get("/staff/12")).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.isHourly").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.wage").value(20)).andExpect(status().isOk());
	}
	
	@Test
	public void get_staffByIdTest_Fail() throws Exception {
		when(staffService.findOneById(anyInt())).thenReturn(Optional.empty());
		mockMvc.perform(MockMvcRequestBuilders.get("/staff/15")).andExpect(status().isBadRequest());
	}

	@Test
	public void get_AllStaffTest_OK() throws Exception {
		Staff staff1 = new Staff();
		staff1.setStaffId(18);
		staff1.setName("Bruce Wayne");
		staff1.setIsHourly(false);
		staff1.setEmail("iambatman@gmail.com");
		staff1.setWage(5000);

		Staff staff2 = new Staff();
		staff2.setStaffId(20);
		staff2.setName("Clark Kent");
		staff2.setIsHourly(false);
		staff2.setEmail("super_man@gmail.com");
		staff2.setWage(4000);

		List<Staff> staffList = new ArrayList<Staff>();
		staffList.add(staff1);
		staffList.add(staff2);

		when(staffService.findAllStaff()).thenReturn(staffList);

		mockMvc.perform(MockMvcRequestBuilders.get("/staff")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].staffId").value(18))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Bruce Wayne"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("iambatman@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].isHourly").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].wage").value(5000))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].staffId").value(20))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Clark Kent"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("super_man@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].isHourly").value(false))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].wage").value(4000));
	}
	
	@Test
	public void delete_staffByIdText_OK() throws Exception {

		Staff staff = new Staff();
		staff.setName("Joey trib");
		staff.setIsHourly(true);
		staff.setEmail("howyoudoing@gmail.com");
		staff.setWage(55500);

		Optional<Staff> staffMember = Optional.of(staff);
		when(staffService.findOneById(anyInt())).thenReturn(staffMember);

		mockMvc.perform(MockMvcRequestBuilders.delete("/staff/12")).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	public void update_staffTest_OK() throws Exception {

		Staff staff = new Staff();
		staff.setName("Kramer");
		staff.setIsHourly(false);
		staff.setEmail("crazy_kram@gmail.com");
		staff.setWage(20000);

		Optional<Staff> staffMember = Optional.of(staff);
		when(staffService.findOneById(anyInt())).thenReturn(staffMember);

		mockMvc.perform(MockMvcRequestBuilders.put("/staff/15").content(new ObjectMapper().writeValueAsString(staff))
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void patch_staffTest_OK() throws Exception {

		Staff staff = new Staff();
		staff.setName("Kramer");
		staff.setIsHourly(false);
		staff.setEmail("crazy_kram@gmail.com");
		staff.setWage(20000);

		Optional<Staff> staffMember = Optional.of(staff);
		when(staffService.findOneById(anyInt())).thenReturn(staffMember);
		
		String patchInJson = "{\"email\":\"Hugh2020@yahoo.com\"}";
		
		when(patchService.map(org.mockito.ArgumentMatchers.any(),
				org.mockito.ArgumentMatchers.any())).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/staff/15").content(patchInJson)
				.contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	public void patch_staffTest_NOT_MODIFIED() throws Exception {

		Staff staff = new Staff();
		staff.setName("Kramer");
		staff.setIsHourly(false);
		staff.setEmail("crazy_kram@gmail.com");
		staff.setWage(20000);

		Optional<Staff> staffMember = Optional.of(staff);
		when(staffService.findOneById(anyInt())).thenReturn(staffMember);
		
		String patchInJson = "{\"email\":\"crazy_kram@gmail.com\"}";
		
		when(patchService.map(org.mockito.ArgumentMatchers.any(),
				org.mockito.ArgumentMatchers.any())).thenReturn(false);
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/staff/15").content(patchInJson)
				.contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNotModified());

	}
}
