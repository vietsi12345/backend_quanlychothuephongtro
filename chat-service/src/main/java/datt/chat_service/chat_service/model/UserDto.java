package datt.chat_service.chat_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private  Long id;

    private String password;
    private String email;
    private String role;
    private String phone;
    private String fullName;
}
