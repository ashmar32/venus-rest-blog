package ashmar32.venusrestblog.repository;


import ashmar32.venusrestblog.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {

}
