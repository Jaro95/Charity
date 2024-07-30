package pl.coderslab.charity.domain.category;

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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;
    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryAddRequest categoryAddRequest;


    @BeforeEach
    void setUp() {
        category = TestDataFactory.createCategory();
        categoryAddRequest = new CategoryAddRequest("category 2");
        categoryRequest = new CategoryRequest("category updated");
    }

    @Test
    void getAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> categoryList = categoryService.getAllCategories();

        assertNotNull(categoryList);
        assertEquals(1, categoryList.size());
        assertEquals(category.getName(),categoryList.get(0).getName());
    }

    @Test
    void getCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryService.getCategory(1L);

        assertTrue(foundCategory.isPresent());
        assertEquals(category.getName(),foundCategory.get().getName());
    }

    @Test
    void addCategory() {
        categoryService.addCategory(categoryAddRequest);

        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> updateCategory = categoryService.updateCategory(1L, categoryRequest);

        assertTrue(updateCategory.isPresent());
        assertEquals(categoryRequest.name(),updateCategory.get().getName());
        verify(categoryRepository,times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> deleteCategory = categoryService.deleteCategory(1L);
        assertTrue(deleteCategory.isPresent());
        verify(categoryRepository,times(1)).delete(any(Category.class));
    }
}