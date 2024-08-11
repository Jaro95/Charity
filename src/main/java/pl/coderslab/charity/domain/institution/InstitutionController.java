package pl.coderslab.charity.domain.institution;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/institutions")
@Tag(name = "Institution")
public class InstitutionController {

    private final InstitutionService institutionService;

    @GetMapping("")
    public InstitutionListResponse getAllInstitutions() {
        return new InstitutionListResponse(institutionService.getAllInstitutions());
    }

    @GetMapping("/{id}")
    public InstitutionResponse getInstitution(@PathVariable Long id) {
        return new InstitutionResponse(institutionService.getInstitution(id));
    }

    @PostMapping("/add")
    public InstitutionAddResponse addInstitution(@RequestBody @Valid InstitutionAddRequest institutionAddRequest) {
        return new InstitutionAddResponse(institutionService.addInstitution(institutionAddRequest));
    }

    @PutMapping("/{id}")
    public InstitutionResponse updateInstitution(@PathVariable Long id, @RequestBody @Valid InstitutionRequest institutionRequest) {
        return new InstitutionResponse(institutionService.updateInstitution(id,institutionRequest));
    }

    @DeleteMapping("/{id}")
    public InstitutionResponse deleteInstitution(@PathVariable Long id) {
        return new InstitutionResponse(institutionService.deleteInstitution(id));
    }
}
