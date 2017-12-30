package be.rmangels.filemanager.api.user.mapper;

import be.rmangels.filemanager.api.user.dto.UserDto;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.inject.Named;
import java.security.Principal;
import java.util.LinkedHashMap;

@Named
public class PrincipalToUserDtoMapper {

    public UserDto mapToUserDto(Principal principal) {
        UserDto userDto = new UserDto().setFullname(principal.getName());
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication authentication = (OAuth2Authentication) principal;
            LinkedHashMap<String, String> object = (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
            return userDto
                    .setFullname(object.get("name"))
                    .setEmail(object.get("email"))
                    .setPicture(object.get("picture"));
        }
        return userDto;
    }
}
