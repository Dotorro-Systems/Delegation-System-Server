package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.dto.NoteDTO;
import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.Department;
import com.Dotorro.DelegationSystemServer.model.Note;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.repository.NoteRepository;
import com.Dotorro.DelegationSystemServer.utils.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;
    private Note note;
    private NoteDTO noteDTO;

    private User user;
    private Delegation delegation;

    @BeforeEach
    void setUp(){

    }

    @Test
    void shouldValidateNote(){
        Note note = new Note(new Delegation(), new User(), "notatka", LocalDateTime.of(2012,2,
                12,2,22,2));
        assertDoesNotThrow(() -> noteService.validateNote(note));
    }

    @Test
    void shouldThrowExceptionForNullDepartment() {
        Note note = new Note(null, new User(), "notatka", LocalDateTime.of(2012,2,
                12,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            noteService.validateNote(note);
        });
        assertEquals("Delegation not found", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionForNullUser() {
        Note note = new Note(new Delegation(), null, "notatka", LocalDateTime.of(2012,2,
                12,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            noteService.validateNote(note);
        });
        assertEquals("User not found", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionForFutureTime() {
        Note note = new Note(new Delegation(), new User(), "notatka", LocalDateTime.of(2412,2,
                12,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            noteService.validateNote(note);
        });
        assertEquals("The date can not be from the future", exception.getMessage());
    }
}
