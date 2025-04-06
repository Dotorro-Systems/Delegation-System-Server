package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.DepartmentController;
import com.Dotorro.DelegationSystemServer.controller.UserController;
import com.Dotorro.DelegationSystemServer.dto.DepartmentDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DepartmentService;
import com.Dotorro.DelegationSystemServer.service.JWTService;
import com.Dotorro.DelegationSystemServer.service.UserService;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DepartmentControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private DepartmentService departmentService;

    @MockitoBean
    private JWTService jwtService;

    @Test
    public void getDepartmentsTest() throws Exception
    {
        Department testDepartment1 = new Department(1L,"testDepartment1");
        Department testDepartment2 = new Department(2L,"testDepartment2");
        Department testDepartment3 = new Department(3L,"testDepartment3");
        Department testDepartment4 = new Department(4L,"testDepartment4");
        List<Department> testDepartments = Arrays.asList(testDepartment1, testDepartment2, testDepartment3, testDepartment4);

        when(departmentService.getAllDepartments()).thenReturn(testDepartments);

        mvc.perform(get("/departments/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[0].name").value("testDepartment1"))
                .andExpect(jsonPath("[1].id").value(2L))
                .andExpect(jsonPath("[1].name").value("testDepartment2"))
                .andExpect(jsonPath("[2].id").value(3L))
                .andExpect(jsonPath("[2].name").value("testDepartment3"))
                .andExpect(jsonPath("[3].id").value(4L))
                .andExpect(jsonPath("[3].name").value("testDepartment4"));
    }

    @Test
    public void getDepartmentByIdTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testName");

        when(departmentService.getDepartmentById(anyLong())).thenReturn(testDepartment);

        mvc.perform(get("/departments/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"));
    }

    @Test
    public void updateDepartmentTest() throws Exception
    {
        DepartmentDTO testDepartmentDTO = new DepartmentDTO("testName");
        Department updatedDepartment = new Department(1L,"testName");

        when(departmentService.updateDepartment(anyLong(),any(DepartmentDTO.class))).thenReturn(updatedDepartment);

        ObjectMapper objectMapper = new ObjectMapper();
        String testDepartmentDTOJson = objectMapper.writeValueAsString(testDepartmentDTO);

        mvc.perform(put("/departments/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDepartmentDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"));
    }

    @Test
    public void deleteDepartmentByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(departmentService).deleteDepartment(id);

        mvc.perform(delete("/departments/{id}",id))
                .andExpect(status().isOk());
    }

    @Test
    public void createDepartmentTest() throws Exception
    {
        DepartmentDTO testDepartmentDTO = new DepartmentDTO("testName");
        Department testDepartment = new Department(1L,"testName");

        when(departmentService.createDepartment(any(DepartmentDTO.class))).thenReturn(testDepartment);

        ObjectMapper objectMapper = new ObjectMapper();
        String testDepartmentDTOJson = objectMapper.writeValueAsString(testDepartmentDTO);

        mvc.perform(post("/departments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDepartmentDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("testName"));
    }
}
