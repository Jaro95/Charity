package pl.coderslab.charity.domain.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public CategoryAddRequest addCategory(CategoryAddRequest categoryAddRequest) {

        categoryRepository.save(Category.builder()
                .name(categoryAddRequest.name())
                .build());
        log.info("Added new category:\n{}\n{}", categoryAddRequest.name());
        return categoryAddRequest;
    }

    public Optional<Category> updateCategory(Long id, CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(c ->
        {
            Optional.ofNullable(categoryRequest.name()).ifPresent(name ->
                    c.setName(c.getName().equals(name) ? c.getName() : name));
            categoryRepository.save(c);
            log.info("Updated category:\n {}", c);
        });
        return category;
    }

    public Optional<Category> deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(c -> {
            categoryRepository.delete(c);
            log.info("Deleted category:\n {}", c);
        });
        return category;
    }
}
