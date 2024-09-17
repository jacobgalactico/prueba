package grupo1.caso_practico.repos;

import grupo1.caso_practico.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByPasswordIgnoreCase(String password);

}
