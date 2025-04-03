package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.DelegationDepartmentController;
import com.Dotorro.DelegationSystemServer.controller.DelegationUserController;
import com.Dotorro.DelegationSystemServer.dto.DelegationDepartmentDTO;
import com.Dotorro.DelegationSystemServer.dto.DelegationUserDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.service.DelegationDepartmentService;
import com.Dotorro.DelegationSystemServer.service.DelegationUserService;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
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

@WebMvcTest(DelegationUserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DelegationUserControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private DelegationUserService delegationUserService;

    @Test
    public void getDelegationUsersTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser1 = new User(1L,"testFirstName1","testLastName1","testPassword1","testPhone1","testEmail1",testRole,testDepartment);
        User testUser2 = new User(2L,"testFirstName2","testLastName2","testPassword2","testPhone2","testEmail2",testRole,testDepartment);

        DelegationUserKey testDelegationUserKey1 = new DelegationUserKey(1L,1L);
        DelegationUserKey testDelegationUserKey2 = new DelegationUserKey(1L,2L);

        DelegationUser testDelegationUser1 = new DelegationUser(testDelegationUserKey1,testDelegation,testUser1);
        DelegationUser testDelegationUser2 = new DelegationUser(testDelegationUserKey2,testDelegation,testUser2);
        List<DelegationUser> testDelegationUsers = Arrays.asList(testDelegationUser1, testDelegationUser2);

        when(delegationUserService.getAllDelegationUsers()).thenReturn(testDelegationUsers);

        mvc.perform(get("/delegationUsers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id.delegationId").value(1L))
                .andExpect(jsonPath("[0].id.userId").value(1L))
                .andExpect(jsonPath("[0].delegation.id").value(1L))
                .andExpect(jsonPath("[0].user.id").value(1L))
                .andExpect(jsonPath("[1].id.delegationId").value(1L))
                .andExpect(jsonPath("[1].id.userId").value(2L))
                .andExpect(jsonPath("[1].delegation.id").value(1L))
                .andExpect(jsonPath("[1].user.id").value(2L));
    }

    @Test
    public void getDelegationDepartmentByDelegationIdUserIdTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationUserKey testDelegationUserKey = new DelegationUserKey(1L,1L);

        DelegationUser testDelegationUser = new DelegationUser(testDelegationUserKey,testDelegation,testUser);

        when(delegationUserService.getDelegationUserByDelegationIdUserId(1L, 1L)).thenReturn(testDelegationUser);

        mvc.perform(get("/delegationUsers/{delegationId}/{userId}",1L,1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id.delegationId").value(1L))
                .andExpect(jsonPath("id.userId").value(1L))
                .andExpect(jsonPath("delegation.id").value(1L))
                .andExpect(jsonPath("user.id").value(1L));
    }

    @Test
    public void updateDelegationUserTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationUserKey testDelegationUserKey = new DelegationUserKey(1L,1L);

        DelegationUserDTO testDelegationUserDTO = new DelegationUserDTO(1L,1L);
        DelegationUser updatedDelegationUser = new DelegationUser(testDelegationUserKey,testDelegation,testUser);

        when(delegationUserService.updateDelegationUser(anyLong(),anyLong(),any(DelegationUserDTO.class))).thenReturn(updatedDelegationUser);

        ObjectMapper objectMapper = new ObjectMapper();
        String testDelegationUserDTOJson = objectMapper.writeValueAsString(testDelegationUserDTO);

        mvc.perform(put("/delegationUsers/{delegationId}/{userId}",1L,1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDelegationUserDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id.delegationId").value(1L))
                .andExpect(jsonPath("id.UserId").value(1L))
                .andExpect(jsonPath("delegation.id").value(1L))
                .andExpect(jsonPath("user.id").value(1L));
    }

    @Test
    public void deleteDelegationUserTest() throws Exception
    {
        Long delegationId = 1L;
        Long userId = 1L;

        doNothing().when(delegationUserService).deleteDelegationUser(delegationId, userId);

        mvc.perform(delete("/delegationUsers/{delegationId}/{userId}",delegationId,userId))
                .andExpect(status().isOk());
    }

    @Test
    public void createDelegationUserTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationUserKey testDelegationUserKey = new DelegationUserKey(1L,1L);

        DelegationUserDTO testDelegationUserDTO = new DelegationUserDTO(1L,1L);
        DelegationUser testDelegationUser = new DelegationUser(testDelegationUserKey,testDelegation,testUser);

        when(delegationUserService.createDelegationUser(any(DelegationUserDTO.class))).thenReturn(testDelegationUser);

        ObjectMapper objectMapper = new ObjectMapper();
        String testDelegationUserDTOJson = objectMapper.writeValueAsString(testDelegationUserDTO);

        mvc.perform(post("/delegationUsers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDelegationUserDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id.delegationId").value(1L))
                .andExpect(jsonPath("id.UserId").value(1L))
                .andExpect(jsonPath("delegation.id").value(1L))
                .andExpect(jsonPath("delegation.title").value("testTitle"))
                .andExpect(jsonPath("delegation.origin").value("testOrigin"))
                .andExpect(jsonPath("delegation.destination").value("testDestination"))
                .andExpect(jsonPath("delegation.status").value(status.toString()))
                .andExpect(jsonPath("delegation.startDate").value(testDate.toString()))
                .andExpect(jsonPath("delegation.endDate").value(testDate.toString()))
                .andExpect(jsonPath("user.id").value(1L))
                .andExpect(jsonPath("user.firstName").value("testFirstName"))
                .andExpect(jsonPath("user.lastName").value("testLastName"))
                .andExpect(jsonPath("user.password").value("testPassword"))
                .andExpect(jsonPath("user.phone").value("testPhone"))
                .andExpect(jsonPath("user.email").value("testEmail"))
                .andExpect(jsonPath("user.role").value("Employee"))
                .andExpect(jsonPath("user.department.id").value(1L))
                .andExpect(jsonPath("user.department.name").value("testDepartment"));
    }
}
