package ashmar32.venusrestblog.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

// THIS IS THE USER DTO RETURNER WHEN CALLING/ API/USERS (OR FETCH ALL USERS)
public class UpdateUserDto {
    private long id;
    private String username;
    private String email;

//    TODO: add constructors, getters, and setters: completed by lombok
//    TODO: add a toString() override: completed by lombok

}
