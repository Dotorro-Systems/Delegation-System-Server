package com.Dotorro.DelegationSystemServer.controller_test;

import com.Dotorro.DelegationSystemServer.controller.ExpenseController;
import com.Dotorro.DelegationSystemServer.controller.NoteController;
import com.Dotorro.DelegationSystemServer.dto.ExpenseDTO;
import com.Dotorro.DelegationSystemServer.dto.NoteDTO;
import com.Dotorro.DelegationSystemServer.model.*;
import com.Dotorro.DelegationSystemServer.service.ExpenseService;
import com.Dotorro.DelegationSystemServer.service.NoteService;
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

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private NoteService noteService;

    @Test
    public void getNotesTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        Note testNote1 = new Note(1L,testDelegation,testUser,"testContent",testDate);
        Note testNote2 = new Note(2L,testDelegation,testUser,"testContent",testDate);
        List<Note> testNotes = Arrays.asList(testNote1, testNote2);

        when(noteService.getAllNotes()).thenReturn(testNotes);

        mvc.perform(get("/notes/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[1].id").value(2L));
    }

    @Test
    public void getNoteByIdTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        Note testNote = new Note(1L,testDelegation,testUser,"testContent",testDate);

        when(noteService.getNoteById(anyLong())).thenReturn(testNote);

        mvc.perform(get("/notes/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.delegation.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.content").value("testContent"))
                .andExpect(jsonPath("$.createdAt").value(testDate.toString()));
    }

    @Test
    public void updateNoteTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        NoteDTO testNoteDTO = new NoteDTO(1L,1L,"testContent",testDate);
        Note testNote = new Note(1L,testDelegation,testUser,"testContent",testDate);

        when(noteService.updateNote(anyLong(),any(NoteDTO.class))).thenReturn(testNote);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testNoteDTOJson = objectMapper.writeValueAsString(testNoteDTO);

        mvc.perform(put("/notes/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testNoteDTOJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void deleteNoteByIdTest() throws Exception
    {
        Long id = 1L;

        doNothing().when(noteService).deleteNote(id);

        mvc.perform(delete("/notes/{id}",id))
                .andExpect(status().isOk());
    }

    @Test
    public void createNoteTest() throws Exception
    {
        Department testDepartment = new Department(1L,"testDepartment");
        UserRole testRole = UserRole.Employee;
        User testUser = new User(1L,"testFirstName","testLastName","testPassword","testPhone","testEmail",testRole,testDepartment);
        DelegationStatus status = DelegationStatus.Active;
        LocalDateTime testDate = LocalDateTime.of(2025,1,1,1,1,1);
        Delegation testDelegation = new Delegation(1L,"testTitle","testOrigin","testDestination",status,testDate,testDate);

        NoteDTO testNoteDTO = new NoteDTO(1L,1L,"testContent",testDate);
        Note testNote = new Note(1L,testDelegation,testUser,"testContent",testDate);

        when(noteService.createNote(any(NoteDTO.class))).thenReturn(testNote);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String testNoteDTOJson = objectMapper.writeValueAsString(testNoteDTO);

        mvc.perform(post("/notes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testNoteDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.delegation.id").value(1L))
                .andExpect(jsonPath("$.user.id").value(1L))
                .andExpect(jsonPath("$.content").value("testContent"))
                .andExpect(jsonPath("$.createdAt").value(testDate.toString()));
    }
}
