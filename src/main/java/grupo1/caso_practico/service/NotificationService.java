package grupo1.caso_practico.service;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.Notification;
import grupo1.caso_practico.domain.User;
import grupo1.caso_practico.model.NotificationDTO;
import grupo1.caso_practico.repos.EventRepository;
import grupo1.caso_practico.repos.NotificationRepository;
import grupo1.caso_practico.repos.UserRepository;
import grupo1.caso_practico.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public NotificationService(final NotificationRepository notificationRepository,
            final UserRepository userRepository, final EventRepository eventRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public List<NotificationDTO> findAll() {
        final List<Notification> notifications = notificationRepository.findAll(Sort.by("id"));
        return notifications.stream()
                .map(notification -> mapToDTO(notification, new NotificationDTO()))
                .toList();
    }

    public NotificationDTO get(final Long id) {
        return notificationRepository.findById(id)
                .map(notification -> mapToDTO(notification, new NotificationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NotificationDTO notificationDTO) {
        final Notification notification = new Notification();
        mapToEntity(notificationDTO, notification);
        return notificationRepository.save(notification).getId();
    }

    public void update(final Long id, final NotificationDTO notificationDTO) {
        final Notification notification = notificationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(notificationDTO, notification);
        notificationRepository.save(notification);
    }

    public void delete(final Long id) {
        notificationRepository.deleteById(id);
    }

    private NotificationDTO mapToDTO(final Notification notification,
            final NotificationDTO notificationDTO) {
        notificationDTO.setId(notification.getId());
        notificationDTO.setNotificationTime(notification.getNotificationTime());
        notificationDTO.setNotificationStatus(notification.getNotificationStatus());
        notificationDTO.setMessage(notification.getMessage());
        notificationDTO.setRecipient(notification.getRecipient() == null ? null : notification.getRecipient().getId());
        notificationDTO.setEvent(notification.getEvent() == null ? null : notification.getEvent().getId());
        return notificationDTO;
    }

    private Notification mapToEntity(final NotificationDTO notificationDTO,
            final Notification notification) {
        notification.setNotificationTime(notificationDTO.getNotificationTime());
        notification.setNotificationStatus(notificationDTO.getNotificationStatus());
        notification.setMessage(notificationDTO.getMessage());
        final User recipient = notificationDTO.getRecipient() == null ? null : userRepository.findById(notificationDTO.getRecipient())
                .orElseThrow(() -> new NotFoundException("recipient not found"));
        notification.setRecipient(recipient);
        final Event event = notificationDTO.getEvent() == null ? null : eventRepository.findById(notificationDTO.getEvent())
                .orElseThrow(() -> new NotFoundException("event not found"));
        notification.setEvent(event);
        return notification;
    }

}
