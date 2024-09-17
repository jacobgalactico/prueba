package grupo1.caso_practico.service;

import grupo1.caso_practico.domain.AccessLog;
import grupo1.caso_practico.domain.User;
import grupo1.caso_practico.model.AccessLogDTO;
import grupo1.caso_practico.repos.AccessLogRepository;
import grupo1.caso_practico.repos.UserRepository;
import grupo1.caso_practico.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final UserRepository userRepository;

    public AccessLogService(final AccessLogRepository accessLogRepository,
            final UserRepository userRepository) {
        this.accessLogRepository = accessLogRepository;
        this.userRepository = userRepository;
    }

    public List<AccessLogDTO> findAll() {
        final List<AccessLog> accessLogs = accessLogRepository.findAll(Sort.by("id"));
        return accessLogs.stream()
                .map(accessLog -> mapToDTO(accessLog, new AccessLogDTO()))
                .toList();
    }

    public AccessLogDTO get(final Long id) {
        return accessLogRepository.findById(id)
                .map(accessLog -> mapToDTO(accessLog, new AccessLogDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AccessLogDTO accessLogDTO) {
        final AccessLog accessLog = new AccessLog();
        mapToEntity(accessLogDTO, accessLog);
        return accessLogRepository.save(accessLog).getId();
    }

    public void update(final Long id, final AccessLogDTO accessLogDTO) {
        final AccessLog accessLog = accessLogRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(accessLogDTO, accessLog);
        accessLogRepository.save(accessLog);
    }

    public void delete(final Long id) {
        accessLogRepository.deleteById(id);
    }

    private AccessLogDTO mapToDTO(final AccessLog accessLog, final AccessLogDTO accessLogDTO) {
        accessLogDTO.setId(accessLog.getId());
        accessLogDTO.setAccessTime(accessLog.getAccessTime());
        accessLogDTO.setIsSuccessful(accessLog.getIsSuccessful());
        accessLogDTO.setDetails(accessLog.getDetails());
        accessLogDTO.setUser(accessLog.getUser() == null ? null : accessLog.getUser().getId());
        return accessLogDTO;
    }

    private AccessLog mapToEntity(final AccessLogDTO accessLogDTO, final AccessLog accessLog) {
        accessLog.setAccessTime(accessLogDTO.getAccessTime());
        accessLog.setIsSuccessful(accessLogDTO.getIsSuccessful());
        accessLog.setDetails(accessLogDTO.getDetails());
        final User user = accessLogDTO.getUser() == null ? null : userRepository.findById(accessLogDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        accessLog.setUser(user);
        return accessLog;
    }

}
