package grupo1.caso_practico.repos;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.Notification;
import grupo1.caso_practico.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Notification findFirstByRecipient(User user);

    Notification findFirstByEvent(Event event);

}
