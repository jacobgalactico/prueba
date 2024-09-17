package grupo1.caso_practico.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @UserUsernameUnique
    private String username;

    @NotNull
    @Size(max = 255)
    @UserPasswordUnique
    private String password;

    @NotNull
    private UserRole role;

    @NotNull
    @JsonProperty("isActive")
    private Boolean isActive;

}
