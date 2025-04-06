package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.DelegationDepartmentController;
import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.DelegationDepartment;
import com.Dotorro.DelegationSystemServer.model.DelegationDepartmentKey;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.service.DelegationDepartmentService;
import com.Dotorro.DelegationSystemServer.service.JWTService;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(DelegationDepartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DelegationDepartmentControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private DelegationDepartmentService delegationDepartmentService;

    @MockitoBean
    private JWTService jwtService;

    @Test
    public void getDelegationDepartmentsTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment1 = new Department(1L,"testName1");
        Department testDepartment2 = new Department(2L,"testName2");
        DelegationDepartmentKey testDelegationDepartmentKey1 = new DelegationDepartmentKey(1L,1L);
        DelegationDepartmentKey testDelegationDepartmentKey2 = new DelegationDepartmentKey(1L,2L);

        DelegationDepartment testDelegationDepartment1 = new DelegationDepartment(testDelegationDepartmentKey1,testDelegation,testDepartment1);
        DelegationDepartment testDelegationDepartment2 = new DelegationDepartment(testDelegationDepartmentKey2,testDelegation,testDepartment2);
        List<DelegationDepartment> testDelegationDepartments = Arrays.asList(testDelegationDepartment1, testDelegationDepartment2);

        when(delegationDepartmentService.getAllDelegationDepartments()).thenReturn(testDelegationDepartments);

        mvc.perform(get("/delegationDepartments/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id.delegationId").value(1L))
                .andExpect(jsonPath("[0].id.departmentId").value(1L))
                .andExpect(jsonPath("[0].delegation.id").value(1L))
                .andExpect(jsonPath("[0].department.id").value(1L))
                .andExpect(jsonPath("[1].id.delegationId").value(1L))
                .andExpect(jsonPath("[1].id.departmentId").value(2L))
                .andExpect(jsonPath("[1].delegation.id").value(1L))
                .andExpect(jsonPath("[1].department.id").value(2L));
    }

    @Test
    public void getDelegationDepartmentByDelegationIdDepartmentIdTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(1L,"testName");
        DelegationDepartmentKey testDelegationDepartmentKey = new DelegationDepartmentKey(1L,1L);

        DelegationDepartment testDelegationDepartment = new DelegationDepartment(testDelegationDepartmentKey,testDelegation,testDepartment);

        when(delegationDepartmentService.getDelegationDepartmentByDelegationIdDepartmentId(1L, 1L)).thenReturn(testDelegationDepartment);

        mvc.perform(get("/delegationDepartments/{delegationId}/{departmentId}",1L,1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id.delegationId").value(1L))
                .andExpect(jsonPath("id.departmentId").value(1L))
                .andExpect(jsonPath("delegation.id").value(1L))
                .andExpect(jsonPath("department.id").value(1L));
    }

    @Test
    public void updateDelegationDepartmentTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(2L,"testName");
        DelegationDepartmentKey testDelegationDepartmentKey = new DelegationDepartmentKey(1L,2L);

        DelegationDepartmentDTO testDelegationDepartmentDTO = new DelegationDepartmentDTO(1L,2L);
        DelegationDepartment updatedDelegationDepartment = new DelegationDepartment(testDelegationDepartmentKey,testDelegation,testDepartment);

        when(delegationDepartmentService.updateDelegationDepartment(anyLong(),anyLong(),any(DelegationDepartmentDTO.class))).thenReturn(updatedDelegationDepartment);

        ObjectMapper objectMapper = new ObjectMapper();
        String testDelegationDepartmentDTOJson = objectMapper.writeValueAsString(testDelegationDepartmentDTO);

        mvc.perform(put("/delegationDepartments/{delegationId}/{departmentId}",1L,2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDelegationDepartmentDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id.delegationId").value(1L))
                .andExpect(jsonPath("id.departmentId").value(2L))
                .andExpect(jsonPath("delegation.id").value(1L))
                .andExpect(jsonPath("department.id").value(2L));
    }

    @Test
    public void deleteDelegationDepartmentByDelegationIdDepartmentIdTest() throws Exception
    {
        Long delegationId = 1L;
        Long departmentId = 1L;

        doNothing().when(delegationDepartmentService).deleteDelegationDepartment(delegationId, departmentId);

        mvc.perform(delete("/delegationDepartments/{delegationId}/{departmentId}",delegationId,departmentId))
                .andExpect(status().isOk());
    }

    @Test
    public void createDelegationDepartmentTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(1L,"testName");
        DelegationDepartmentKey testDelegationDepartmentKey = new DelegationDepartmentKey(1L,1L);

        DelegationDepartmentDTO testDelegationDepartmentDTO = new DelegationDepartmentDTO(1L,1L);
        DelegationDepartment testDelegationDepartment = new DelegationDepartment(testDelegationDepartmentKey,testDelegation,testDepartment);

        when(delegationDepartmentService.createDelegationDepartment(any(DelegationDepartmentDTO.class))).thenReturn(testDelegationDepartment);

        ObjectMapper objectMapper = new ObjectMapper();
        String testDelegationDepartmentDTOJson = objectMapper.writeValueAsString(testDelegationDepartmentDTO);

        mvc.perform(post("/delegationDepartments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDelegationDepartmentDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id.delegationId").value(1L))
                .andExpect(jsonPath("id.departmentId").value(1L))
                .andExpect(jsonPath("delegation.id").value(1L))
                .andExpect(jsonPath("delegation.title").value("testTitle"))
                .andExpect(jsonPath("delegation.origin").value("testOrigin"))
                .andExpect(jsonPath("delegation.destination").value("testDestination"))
                .andExpect(jsonPath("delegation.status").value(status.toString()))
                .andExpect(jsonPath("delegation.startDate").value(testDate.toString()))
                .andExpect(jsonPath("delegation.endDate").value(testDate.toString()))
                .andExpect(jsonPath("department.id").value(1L))
                .andExpect(jsonPath("department.name").value("testName"));
    }
}
