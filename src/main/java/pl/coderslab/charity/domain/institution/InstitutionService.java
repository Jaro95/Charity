package pl.coderslab.charity.domain.institution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public List<Institution> getAllInstitutions() {
        return institutionRepository.findAll();
    }

    public Optional<Institution> getInstitution(Long id) {
        return institutionRepository.findById(id);
    }

    public InstitutionAddRequest addInstitution(InstitutionAddRequest institutionAddRequest) {
            institutionRepository.save(Institution.builder()
                    .name(institutionAddRequest.name())
                    .description(institutionAddRequest.description())
                    .build());
            log.info("Added new institution:\n{}\n{}", institutionAddRequest.name(), institutionAddRequest.description());
        return institutionAddRequest;
    }

    public Optional<Institution> updateInstitution(Long id, InstitutionRequest institutionRequest) {
        Optional<Institution> institution = institutionRepository.findById(id);
        institution.ifPresent(i -> {
                Optional.ofNullable(institutionRequest.name()).ifPresent(name ->
                        i.setName(i.getName().equals(name) ? i.getName() : name)
                        );
                Optional.ofNullable(institutionRequest.description()).ifPresent(description ->
                        i.setDescription(i.getDescription().equals(description) ? i.getDescription() : description)
                        );
                institutionRepository.save(i);
                log.info("Updated institution: {}", i);

        });
        return institution;
    }

    public Optional<Institution> deleteInstitution(Long id) {
        Optional<Institution> institution = institutionRepository.findById(id);
        institution.ifPresent(i -> {
            institutionRepository.delete(i);
            log.info("Deleted institution: {}", i);
        });
        return institution;
    }
}