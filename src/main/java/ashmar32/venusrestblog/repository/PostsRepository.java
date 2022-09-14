package ashmar32.venusrestblog.repository;

import ashmar32.venusrestblog.data.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {

    }
