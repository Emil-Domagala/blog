package backend.blog.service;

import java.util.List;
import java.util.UUID;

import backend.blog.domain.entities.Category;

public interface CategoryService {
    List<Category> getCategories();

    Category createCategory(Category category);

    void deleteCategory(UUID id);
}
