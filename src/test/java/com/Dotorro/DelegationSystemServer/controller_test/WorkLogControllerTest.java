package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.NoteController;
import com.Dotorro.DelegationSystemServer.controller.WorkLogController;
import com.Dotorro.DelegationSystemServer.dto.NoteDTO;
import com.Dotorro.DelegationSystemServer.dto.WorkLogDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.service.NoteService;
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

@WebMvcTest(WorkLogController.class)
@AutoConfigureMockMvc(addFilters = false)
public class WorkLogControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private WorkLogService workLogService;

    @Test
    public void getWorkLogsTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail", testRole, testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        WorkLog testWorkLog1 = new WorkLog(1L, testDelegation, testUser, testDate, testDate);
        WorkLog testWorkLog2 = new WorkLog(2L, testDelegation, testUser, testDate, testDate);
        List<WorkLog> testWorkLogs = Arrays.asList(testWorkLog1, testWorkLog2);

        when(workLogService.getAllWorkLogs()).thenReturn(testWorkLogs);

        mvc.perform(get("/workLogs/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[1].id").value(2L));
    }

    @Test
    public void getWorkLogByIdTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        when(workLogService.getWorkLogById(anyLong())).thenReturn(testWorkLog);

        mvc.perform(get("/workLogs/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.delegation.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.startTime").value(testDate.toString()))
                .andExpect(jsonPath("$.endTime").value(testDate.toString()));
    }

    @Test
    public void updateWorkLogTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        WorkLogDTO testWorkLogDTO = new WorkLogDTO(1L,1L,testDate,testDate);
        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        when(workLogService.updateWorkLog(anyLong(),any(WorkLogDTO.class))).thenReturn(testWorkLog);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testWorkLogDTOJson = objectMapper.writeValueAsString(testWorkLogDTO);

        mvc.perform(put("/workLogs/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testWorkLogDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void deleteWorkLogByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(workLogService).deleteWorkLog(id);

        mvc.perform(delete("/workLogs/{id}",id))
                .andExpect(status().isOk());
    }

    @Test
    public void createWorkLogTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.EMPLOYEE;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        WorkLogDTO testWorkLogDTO = new WorkLogDTO(1L,1L,testDate,testDate);
        WorkLog testWorkLog = new WorkLog(1L, testDelegation, testUser, testDate, testDate);

        when(workLogService.createWorkLog(any(WorkLogDTO.class))).thenReturn(testWorkLog);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testWorkLogDTOJson = objectMapper.writeValueAsString(testWorkLogDTO);

        mvc.perform(post("/workLogs/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testWorkLogDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.delegation.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.startTime").value(testDate.toString()))
                .andExpect(jsonPath("$.endTime").value(testDate.toString()));
    }
}
