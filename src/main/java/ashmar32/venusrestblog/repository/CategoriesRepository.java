package ashmar32.venusrestblog.repository;

import ashmar32.venusrestblog.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
