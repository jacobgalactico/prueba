package grupo1.caso_practico.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotificationDTO {

    private Long id;

    @NotNull
    private LocalDateTime notificationTime;

    @NotNull
    private NotificationStatus notificationStatus;

    @NotNull
    @Size(max = 255)
    private String message;

    private Long recipient;

    private Long event;

}
