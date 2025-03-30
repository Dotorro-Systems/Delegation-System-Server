package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.UserController;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.UserService;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void createUserTest() throws Exception
    {
        UserDTO testUserDTO = new UserDTO("testFirstName","testLastName","testPassword","testPhone","testEmail","Employee",1L);
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);

        when(userService.createUser(any(UserDTO.class))).thenReturn(testUser);

        ObjectMapper objectMapper = new ObjectMapper();
        String testUserDTOJson = objectMapper.writeValueAsString(testUserDTO);

        mvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("testFirstName"))
                .andExpect(jsonPath("$.lastName").value("testLastName"))
                .andExpect(jsonPath("$.password").value("testPassword"))
                .andExpect(jsonPath("$.phone").value("testPhone"))
                .andExpect(jsonPath("$.email").value("testEmail"))
                .andExpect(jsonPath("$.role").value("Employee"))
                .andExpect(jsonPath("$.department.id").value(1L))
                .andExpect(jsonPath("$.department.name").value("testDepartment"));
    }

    @Test
    public void getUsersTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser1 = new User(1L,"testFirstName1","testLastName1","testPassword1","testPhone1","testEmail1",testRole,testDepartment);
        User testUser2 = new User(2L,"testFirstName2","testLastName2","testPassword2","testPhone2","testEmail2",testRole,testDepartment);
        List<User> testUsers = Arrays.asList(testUser1, testUser2);

        when(userService.getAllUsers()).thenReturn(testUsers);

        mvc.perform(get("/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[0].firstName").value("testFirstName1"))
                .andExpect(jsonPath("[0].lastName").value("testLastName1"))
                .andExpect(jsonPath("[0].password").value("testPassword1"))
                .andExpect(jsonPath("[0].phone").value("testPhone1"))
                .andExpect(jsonPath("[0].email").value("testEmail1"))
                .andExpect(jsonPath("[0].role").value("Employee"))
                .andExpect(jsonPath("[0].department.id").value(1L))
                .andExpect(jsonPath("[0].department.name").value("testDepartment"))
                .andExpect(jsonPath("[1].id").value(2L))
                .andExpect(jsonPath("[1].firstName").value("testFirstName2"))
                .andExpect(jsonPath("[1].lastName").value("testLastName2"))
                .andExpect(jsonPath("[1].password").value("testPassword2"))
                .andExpect(jsonPath("[1].phone").value("testPhone2"))
                .andExpect(jsonPath("[1].email").value("testEmail2"))
                .andExpect(jsonPath("[1].role").value("Employee"))
                .andExpect(jsonPath("[1].department.id").value(1L))
                .andExpect(jsonPath("[1].department.name").value("testDepartment"));
    }

    @Test
    public void getUserByIdTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);

        when(userService.getUserById(anyLong())).thenReturn(testUser);

        mvc.perform(get("/users/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("testFirstName"))
                .andExpect(jsonPath("$.lastName").value("testLastName"))
                .andExpect(jsonPath("$.password").value("testPassword"))
                .andExpect(jsonPath("$.phone").value("testPhone"))
                .andExpect(jsonPath("$.email").value("testEmail"))
                .andExpect(jsonPath("$.role").value("Employee"))
                .andExpect(jsonPath("$.department.id").value(1L))
                .andExpect(jsonPath("$.department.name").value("testDepartment"));
    }

    @Test
    public void updateUserTest() throws Exception
    {
        UserDTO testUserDTO = new UserDTO("testFirstNameUpdated","testLastName","testPassword","testPhone","testEmail","Employee",1L);
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User updatedUser = new User(1L,"testFirstNameUpdated","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);

        when(userService.updateUser(anyLong(),any(UserDTO.class))).thenReturn(updatedUser);

        ObjectMapper objectMapper = new ObjectMapper();
        String testUserDTOJson = objectMapper.writeValueAsString(testUserDTO);

        mvc.perform(put("/users/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUserDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("testFirstNameUpdated"));
    }

    @Test
    public void updatePasswordTest() throws Exception
    {
        String password = "testUpdatedPassword";
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User updatedUser = new User(1L,"testFirstNameUpdated","testLastName",password,"testPhone","testEmail",testRole,testDepartment);

        when(userService.updatePassword(anyLong(),anyString())).thenReturn(updatedUser);

        ObjectMapper objectMapper = new ObjectMapper();
        String testUpdatedPasswordJson = objectMapper.writeValueAsString(password);

        mvc.perform(put("/users/{id}/password",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testUpdatedPasswordJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.password").value("testUpdatedPassword"));
    }

    @Test
    public void deleteUserByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(userService).deleteUser(id);

        mvc.perform(delete("/users/{id}",id))
                .andExpect(status().isOk());
    }
}
