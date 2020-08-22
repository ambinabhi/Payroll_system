package com.fileee.payrole;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;
import com.fileee.payrole.controllers.WorklogController;
import com.fileee.payrole.exception.ResourceNotFoundException;
import com.fileee.payrole.repository.StaffRepository;
import com.fileee.payrole.repository.WorklogRepository;
import com.fileee.payrole.services.PatchService;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.services.WorklogService;
import com.fileee.payrole.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(WorklogController.class)
class WorklogControllerTest {

	@Autowired
	WorklogController worklogController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StaffService staffService;
	@MockBean
	private StaffRepository staffRepository;
	@MockBean
	private PatchService patchService;
	@MockBean
	private WorklogService worklogService;
	@MockBean
	private WorklogRepository worklogRepository;
	@MockBean
	private ResourceNotFoundException resourceNotFoundException;

	@Test
	public void add_worklogTest_OK() throws Exception {

		Optional<Staff> staffMember = Optional.of(new Staff());
		when(staffService.findOneById(anyInt())).thenReturn(staffMember);

		Worklog worklog = new Worklog();
		worklog.setStaff(staffMember.get());
		worklog.setWorkingHours(8);
		worklog.setWorkingDate(DateUtils.getFormattedDate("2020-07-12"));

		when(worklogService.save(any(Worklog.class))).thenReturn(worklog);

		mockMvc.perform(MockMvcRequestBuilders.post("/worklog/9")
				.content(new ObjectMapper().writeValueAsString(worklog)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.workingHours").value(8))
				.andExpect(status().isCreated());

	}

	@Test
	public void get_allWorklogWithoutDatesTest_OK() throws Exception {
		
		Staff staff = new Staff();
		staff.setStaffId(15);
		staff.setName("Kate Hudson");
		staff.setIsHourly(false);
		staff.setEmail("kathyson1985@gmail.com");
		staff.setWage(2500);

		when(staffService.save(any(Staff.class))).thenReturn(staff);

		when(staffService.findOneById(anyInt())).thenReturn(Optional.of(staff));

		Worklog worklog1 = new Worklog();
		worklog1.setStaff(staff);
		worklog1.setWorkingHours(8);
		worklog1.setWorkingDate(DateUtils.getFormattedDate("2020-07-12"));
		when(worklogService.save(any(Worklog.class))).thenReturn(worklog1);

		Worklog worklog2 = new Worklog();
		worklog2.setStaff(staff);
		worklog2.setWorkingHours(4);
		worklog2.setWorkingDate(DateUtils.getFormattedDate("2020-08-12"));
		when(worklogService.save(any(Worklog.class))).thenReturn(worklog2);

		String fromDate = "";
		String toDate = "";
		mockMvc.perform(MockMvcRequestBuilders.get("/worklog/10")
				.param("from_date", fromDate).param("to_date", toDate))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
	}
}
