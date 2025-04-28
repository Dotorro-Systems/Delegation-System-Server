package com.Dotorro.DelegationSystemServer.service;

import com.Dotorro.DelegationSystemServer.model.Delegation;
import com.Dotorro.DelegationSystemServer.repository.DelegationRepository;
import com.Dotorro.DelegationSystemServer.utils.DelegationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DelegationServiceTest {

    @Mock
    private DelegationRepository delegationRepository;

    @InjectMocks
    private DelegationService delegationService;

    @BeforeEach
    void setUp(){

    }

    @Test
    void shouldValidateDelegation(){
        Delegation delegation = new Delegation("Wyjazd do Chin", "Chiny", "Hong Kong",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> delegationService.validateDelegation(delegation));
    }

    @Test
    void shouldValidateDelegationWithPolishLetterInTitle(){
        Delegation delegation = new Delegation("Wyjazd do Fabryki koralików", "Chiny", "Hong Kong",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> delegationService.validateDelegation(delegation));
    }

    @Test
    void shouldValidateDelegationWithPolishLetterInOrigin(){
        Delegation delegation = new Delegation("Wyjazd do Francji","Łotwa", "Hong Kong",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> delegationService.validateDelegation(delegation));
    }

    @Test
    void shouldValidateDelegationWithPolishLetterInDestination(){
        Delegation delegation = new Delegation("Wyjazd do Francji", "Europa", "Łomża",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> delegationService.validateDelegation(delegation));
    }

    @Test
    void shouldValidateDelegationWithDash(){
        Delegation delegation = new Delegation("Wyjazd do wojewódstwa warmińsko-mazurskiego", "Europa-Afryka",
                "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> delegationService.validateDelegation(delegation));
    }

    @Test
    void shouldValidateDelegationWithCorrectDates(){
        Delegation delegation = new Delegation("Wyjazd do wojewódstwa warmińsko-mazurskiego", "Europa-Afryka",
                "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        assertDoesNotThrow(() -> delegationService.validateDelegation(delegation));
    }

    @Test
    void shouldThrowExceptionForInvalidOrigin(){
        Delegation delegation = new Delegation("Wyjazd na Mazury", "Europa2", "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("Origin must only contain letters", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidTitle(){
        Delegation delegation = new Delegation("Wyjazd na 2Mazury", "Europa", "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("Title must only contain letters", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidDestination(){
        Delegation delegation = new Delegation("Wyjazd na Mazury", "Europa", "Bielsko-Bi4ła",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("Destination must only contain letters", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNonCapitalLetterInDestination(){
        Delegation delegation = new Delegation("Wyjazd na Mazury", "Europa", "bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("Destination must start with capital letter", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNonCapitalLetterInTitle(){
        Delegation delegation = new Delegation("wyjazd na Mazury", "Europa", "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("Title must start with capital letter", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidEndDate(){
        Delegation delegation = new Delegation("Wyjazd na Mazury", "Europa", "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 2,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("End date cannot be earlier than the start date", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNonCapitalLetterInOrigin(){
        Delegation delegation = new Delegation("Wyjazd na Mazury", "europa", "Bielsko-Biała",
                DelegationStatus.Planned, LocalDateTime.of(2012,2, 12,2,22,2),
                LocalDateTime.of(2012,2, 20,2,22,2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delegationService.validateDelegation(delegation);
        });

        assertEquals("Origin must start with capital letter", exception.getMessage());
    }
}
