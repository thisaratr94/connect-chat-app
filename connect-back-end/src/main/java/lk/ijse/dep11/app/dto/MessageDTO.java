package lk.ijse.dep11.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {
    @NotBlank(message = "Chat cannot be empty")
    private String message;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid Email")
    private String email;
}
