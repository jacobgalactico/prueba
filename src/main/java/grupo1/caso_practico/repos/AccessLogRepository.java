package grupo1.caso_practico.repos;

import grupo1.caso_practico.domain.AccessLog;
import grupo1.caso_practico.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

    AccessLog findFirstByUser(User user);

}
