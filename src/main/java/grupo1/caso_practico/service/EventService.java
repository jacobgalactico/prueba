package grupo1.caso_practico.service;

import grupo1.caso_practico.domain.Event;
import grupo1.caso_practico.domain.Notification;
import grupo1.caso_practico.domain.Sensor;
import grupo1.caso_practico.model.EventDTO;
import grupo1.caso_practico.repos.EventRepository;
import grupo1.caso_practico.repos.NotificationRepository;
import grupo1.caso_practico.repos.SensorRepository;
import grupo1.caso_practico.util.NotFoundException;
import grupo1.caso_practico.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SensorRepository sensorRepository;
    private final NotificationRepository notificationRepository;

    public EventService(final EventRepository eventRepository,
            final SensorRepository sensorRepository,
            final NotificationRepository notificationRepository) {
        this.eventRepository = eventRepository;
        this.sensorRepository = sensorRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<EventDTO> findAll() {
        final List<Event> events = eventRepository.findAll(Sort.by("id"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .toList();
    }

    public EventDTO get(final Long id) {
        return eventRepository.findById(id)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getId();
    }

    public void update(final Long id, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setId(event.getId());
        eventDTO.setTime(event.getTime());
        eventDTO.setEventType(event.getEventType());
        eventDTO.setDetails(event.getDetails());
        eventDTO.setSensor(event.getSensor() == null ? null : event.getSensor().getId());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setTime(eventDTO.getTime());
        event.setEventType(eventDTO.getEventType());
        event.setDetails(eventDTO.getDetails());
        final Sensor sensor = eventDTO.getSensor() == null ? null : sensorRepository.findById(eventDTO.getSensor())
                .orElseThrow(() -> new NotFoundException("sensor not found"));
        event.setSensor(sensor);
        return event;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Event event = eventRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Notification eventNotification = notificationRepository.findFirstByEvent(event);
        if (eventNotification != null) {
            referencedWarning.setKey("event.notification.event.referenced");
            referencedWarning.addParam(eventNotification.getId());
            return referencedWarning;
        }
        return null;
    }

}
