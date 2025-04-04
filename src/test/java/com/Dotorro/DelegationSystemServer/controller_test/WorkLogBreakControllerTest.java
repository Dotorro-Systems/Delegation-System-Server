package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.WorkLogBreakController;
import com.Dotorro.DelegationSystemServer.controller.WorkLogController;
import com.Dotorro.DelegationSystemServer.dto.WorkLogBreakDTO;
import com.Dotorro.DelegationSystemServer.dto.WorkLogDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.service.WorkLogBreakService;
import com.Dotorro.DelegationSystemServer.service.WorkLogService;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkLogBreakController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkLogBreakControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private WorkLogBreakService workLogBreakService;

    @Test
    public void getWorkLogsTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        WorkLogBreak testWorkLogBreak1 = new WorkLogBreak(1L, testWorkLog, testDate, testDate);
        WorkLogBreak testWorkLogBreak2 = new WorkLogBreak(2L, testWorkLog, testDate, testDate);
        List<WorkLogBreak> testWorkLogBreaks = Arrays.asList(testWorkLogBreak1, testWorkLogBreak2);

        when(workLogBreakService.getAllWorkLogBreaks()).thenReturn(testWorkLogBreaks);

        mvc.perform(get("/workLogBreaks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[1].id").value(2L));
    }

    @Test
    public void getWorkLogBreakByIdTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        WorkLogBreak testWorkLogBreak = new WorkLogBreak(1L, testWorkLog, testDate, testDate);

        when(workLogBreakService.getWorkLogBreakById(anyLong())).thenReturn(testWorkLogBreak);

        mvc.perform(get("/workLogBreaks/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.workLog.id").value(1L))
                .andExpect(jsonPath("$.startTime").value(testDate.toString()))
                .andExpect(jsonPath("$.endTime").value(testDate.toString()));
    }

    @Test
    public void updateWorkLogBreakTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        WorkLogBreakDTO testWorkLogBreakDTO = new WorkLogBreakDTO(1L,testDate,testDate);
        WorkLogBreak testWorkLogBreak = new WorkLogBreak(1L,testWorkLog,testDate,testDate);

        when(workLogBreakService.updateWorkLogBreak(anyLong(),any(WorkLogBreakDTO.class))).thenReturn(testWorkLogBreak);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testWorkLogBreakDTOJson = objectMapper.writeValueAsString(testWorkLogBreakDTO);

        mvc.perform(put("/workLogBreaks/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testWorkLogBreakDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void deleteWorkLogByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(workLogBreakService).deleteWorkLogBrak(id);

        mvc.perform(delete("/workLogBreaks/{id}",id))
                .andExpect(status().isOk());
    }

    @Test
    public void createWorkLogBreakTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        WorkLogBreakDTO testWorkLogBreakDTO = new WorkLogBreakDTO(1L,testDate,testDate);
        WorkLogBreak testWorkLogBreak = new WorkLogBreak(1L,testWorkLog,testDate,testDate);

        when(workLogBreakService.createWorkLogBreak(any(WorkLogBreakDTO.class))).thenReturn(testWorkLogBreak);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testWorkLogBreakDTOJson = objectMapper.writeValueAsString(testWorkLogBreakDTO);

        mvc.perform(post("/workLogBreaks/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testWorkLogBreakDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.workLog.id").value(1L))
                .andExpect(jsonPath("$.startTime").value(testDate.toString()))
                .andExpect(jsonPath("$.endTime").value(testDate.toString()));
    }
}
