package grupo1.caso_practico.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccessLogDTO {

    private Long id;

    @NotNull
    private LocalDateTime accessTime;

    @NotNull
    @JsonProperty("isSuccessful")
    private Boolean isSuccessful;

    @NotNull
    @Size(max = 255)
    private String details;

    private Long user;

}
