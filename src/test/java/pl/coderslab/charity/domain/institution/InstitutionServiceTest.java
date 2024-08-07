package pl.coderslab.charity.domain.institution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.charity.domain.dataFactory.TestDataFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceTest {

    @Mock
    private InstitutionRepository institutionRepository;
    @InjectMocks
    private InstitutionService institutionService;
    private Institution institution;

    @BeforeEach
    void setUp() {
        institution = TestDataFactory.createInstitution();
    }

    @Test
    void testGetAllInstitutions() {
        when(institutionRepository.findAll()).thenReturn(List.of(institution));

        List<Institution> institutions = institutionService.getAllInstitutions();

        assertNotNull(institutions);
        assertEquals(1 , institutions.size());
        assertEquals(institution.getName(), institutions.get(0).getName());
    }

    @Test
    void testGetInstitution() {
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

        Optional<Institution> foundInstitution = institutionService.getInstitution(1L);

        assertTrue(foundInstitution.isPresent());
        assertEquals(institution.getName(), foundInstitution.get().getName());
    }

    @Test
    void testAddInstitution() {
        InstitutionAddRequest institutionAddRequest = new InstitutionAddRequest("Institution 2", "Description 2");
        institutionService.addInstitution(institutionAddRequest);

        verify(institutionRepository, times(1)).save(any(Institution.class));
    }

    @Test
    void testUpdateInstitution() {
        InstitutionRequest institutionRequest = new InstitutionRequest("Updated Institution", "Updated Description");
        when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

        Optional<Institution> updateInstitution = institutionService.updateInstitution(1L, institutionRequest);

        assertTrue(updateInstitution.isPresent());
        assertEquals(institutionRequest.name(), updateInstitution.get().getName());
        assertEquals(institutionRequest.description(), updateInstitution.get().getDescription());
        verify(institutionRepository, times(1)).save(any(Institution.class));
    }

    @Test
    void testDeleteInstitution() {
    when(institutionRepository.findById(1L)).thenReturn(Optional.of(institution));

    Optional<Institution> deleteInstitution = institutionService.deleteInstitution(1L);

    assertTrue(deleteInstitution.isPresent());
    verify(institutionRepository, times(1)).delete(institution);
    }
}