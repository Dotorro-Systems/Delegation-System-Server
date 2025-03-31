package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.DelegationController;
import com.Dotorro.DelegationSystemServer.controller.UserController;
import com.Dotorro.DelegationSystemServer.dto.DelegationDTO;
import com.Dotorro.DelegationSystemServer.dto.UserDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.service.DelegationService;
import com.Dotorro.DelegationSystemServer.service.UserService;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DelegationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DelegationControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private DelegationService delegationService;

    @Test
    public void getDelegationsTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation1 = new Delegation(1L,"testTitle1","testOrigin1","testDestination1",status,testDate,testDate);
        Delegation testDelegation2 = new Delegation(2L,"testTitle2","testOrigin2","testDestination2",status,testDate,testDate);

        List<Delegation> testDelegations = Arrays.asList(testDelegation1, testDelegation2);

        when(delegationService.getAllDelegations()).thenReturn(testDelegations);

        mvc.perform(get("/delegations/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[0].title").value("testTitle1"))
                .andExpect(jsonPath("[0].origin").value("testOrigin1"))
                .andExpect(jsonPath("[0].destination").value("testDestination1"))
                .andExpect(jsonPath("[0].status").value(status.toString()))
                .andExpect(jsonPath("[0].startDate").value(testDate.toString()))
                .andExpect(jsonPath("[0].endDate").value(testDate.toString()))
                .andExpect(jsonPath("[1].id").value(2L))
                .andExpect(jsonPath("[1].title").value("testTitle2"))
                .andExpect(jsonPath("[1].origin").value("testOrigin2"))
                .andExpect(jsonPath("[1].destination").value("testDestination2"))
                .andExpect(jsonPath("[1].status").value(status.toString()))
                .andExpect(jsonPath("[1].startDate").value(testDate.toString()))
                .andExpect(jsonPath("[1].endDate").value(testDate.toString()));
    }

    @Test
    public void getUserByIdTest() throws Exception
    {
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        when(delegationService.getDelegationById(anyLong())).thenReturn(testDelegation);

        mvc.perform(get("/delegations/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.origin").value("testOrigin"))
                .andExpect(jsonPath("$.destination").value("testDestination"))
                .andExpect(jsonPath("$.status").value(status.toString()))
                .andExpect(jsonPath("$.startDate").value(testDate.toString()))
                .andExpect(jsonPath("$.endDate").value(testDate.toString()));
    }

    @Test
    public void updateDelegationTest() throws Exception
    {
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        DelegationDTO testDelegationDTO = new DelegationDTO("testTitle","testOrigin","testDestination","Active",testDate,testDate);
        DelegationStatus status = DelegationStatus.Active;
        Delegation updatedDelegation = new Delegation(1L,"testTitleUpdated","testOrigin","testDestination",status,testDate,testDate);

        when(delegationService.updateDelegation(anyLong(),any(DelegationDTO.class))).thenReturn(updatedDelegation);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testDelegationDTOJson = objectMapper.writeValueAsString(testDelegationDTO);

        mvc.perform(put("/delegations/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDelegationDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("testTitleUpdated"));
    }

    @Test
    public void deleteDelegationByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(delegationService).deleteDelegation(id);

        mvc.perform(delete("/delegations/{id}",id))
                .andExpect(status().isOk());
    }

    @Test
    public void createDelegationTest() throws Exception
    {
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        DelegationDTO testDelegationDTO = new DelegationDTO("testTitle","testOrigin","testDestination","Active",testDate,testDate);
        DelegationStatus status = DelegationStatus.Active;
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        when(delegationService.createDelegation(any(DelegationDTO.class))).thenReturn(testDelegation);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testDelegationDTOJson = objectMapper.writeValueAsString(testDelegationDTO);

        mvc.perform(post("/delegations/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testDelegationDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.origin").value("testOrigin"))
                .andExpect(jsonPath("$.destination").value("testDestination"))
                .andExpect(jsonPath("$.status").value(status.toString()))
                .andExpect(jsonPath("$.startDate").value(testDate.toString()))
                .andExpect(jsonPath("$.endDate").value(testDate.toString()));
    }

}
