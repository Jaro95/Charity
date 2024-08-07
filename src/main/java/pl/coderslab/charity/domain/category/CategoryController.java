package pl.coderslab.charity.domain.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public CategoryListResponse getAllCategories() {
        return new CategoryListResponse(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategory(@PathVariable Long id) {
        return new CategoryResponse(categoryService.getCategory(id));
    }

    @PostMapping("/add")
    public CategoryAddResponse addCategory(@RequestBody @Valid CategoryAddRequest categoryAddRequest) {
        return new CategoryAddResponse(categoryService.addCategory(categoryAddRequest));
    }

    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest categoryRequest) {
        return new CategoryResponse(categoryService.updateCategory(id,categoryRequest));
    }

    @DeleteMapping("/{id}")
    public CategoryResponse deleteCategory(@PathVariable Long id) {
         return new CategoryResponse(categoryService.deleteCategory(id));
    }
}
