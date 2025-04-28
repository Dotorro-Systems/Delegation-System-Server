package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.model.User;
import com.Dotorro.DelegationSystemServer.model.WorkLog;
import com.Dotorro.DelegationSystemServer.repository.WorkLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WorkLogServiceTest {
    @Mock
    private WorkLogRepository workLogRepository;

    @Mock
    private UserService userService;

    @Mock
    private DelegationService delegationService;

    @InjectMocks
    private WorkLog workLog;
    private WorkLogService workLogService;

    @BeforeEach
    void setUp(){
        workLogService = new WorkLogService(workLogRepository, userService, delegationService);
    }

    @Test
    void shouldValidateWorkLog(){
        WorkLog workLog = new WorkLog(new Delegation(), new User(),
                LocalDateTime.of(2012,2, 20,2,22,2),
                LocalDateTime.of(2012,3, 20,2,22,2));

        assertDoesNotThrow(() -> workLogService.validateWorkLog(workLog));
    }

    @Test
    void shouldThrowExceptionForNullUser(){
        WorkLog workLog = new WorkLog(new Delegation(), null,
                LocalDateTime.of(2012,2, 20,2,22,2),
                LocalDateTime.of(2012,3, 20,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            workLogService.validateWorkLog(workLog);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullDelegation(){
        WorkLog workLog = new WorkLog(null, new User(),
                LocalDateTime.of(2012,2, 20,2,22,2),
                LocalDateTime.of(2012,3, 20,2,22,2));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            workLogService.validateWorkLog(workLog);
        });

        assertEquals("Delegation not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidStartTime(){
        WorkLog workLog = new WorkLog(new Delegation(), new User(),
                LocalDateTime.of(2092,2, 20,2,22,2),
                LocalDateTime.of(2012,3, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            workLogService.validateWorkLog(workLog);
        });

        assertEquals("End time cannot be earlier than the start time", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidEndTime(){
        WorkLog workLog = new WorkLog(new Delegation(), new User(),
                LocalDateTime.of(2012,2, 20,2,22,2),
                LocalDateTime.of(2042,1, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            workLogService.validateWorkLog(workLog);
        });

        assertEquals("Time can't be from the future", exception.getMessage());
    }
}
