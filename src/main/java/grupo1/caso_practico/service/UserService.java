package grupo1.caso_practico.service;

import grupo1.caso_practico.domain.AccessLog;
import grupo1.caso_practico.domain.Notification;
import grupo1.caso_practico.domain.User;
import grupo1.caso_practico.model.UserDTO;
import grupo1.caso_practico.repos.AccessLogRepository;
import grupo1.caso_practico.repos.NotificationRepository;
import grupo1.caso_practico.repos.UserRepository;
import grupo1.caso_practico.util.NotFoundException;
import grupo1.caso_practico.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final AccessLogRepository accessLogRepository;

    public UserService(final UserRepository userRepository,
            final NotificationRepository notificationRepository,
            final AccessLogRepository accessLogRepository) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.accessLogRepository = accessLogRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setIsActive(user.getIsActive());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setIsActive(userDTO.getIsActive());
        return user;
    }

    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public boolean passwordExists(final String password) {
        return userRepository.existsByPasswordIgnoreCase(password);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Notification recipientNotification = notificationRepository.findFirstByRecipient(user);
        if (recipientNotification != null) {
            referencedWarning.setKey("user.notification.recipient.referenced");
            referencedWarning.addParam(recipientNotification.getId());
            return referencedWarning;
        }
        final AccessLog userAccessLog = accessLogRepository.findFirstByUser(user);
        if (userAccessLog != null) {
            referencedWarning.setKey("user.accessLog.user.referenced");
            referencedWarning.addParam(userAccessLog.getId());
            return referencedWarning;
        }
        return null;
    }

}
