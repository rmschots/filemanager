package be.rmangels.filemanager.api.user.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class UserDto {
    public String fullname;
    public String email;
    public String picture;

    public UserDto() {
    }
}
