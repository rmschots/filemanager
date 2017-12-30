package be.rmangels.filemanager.api.user;

import be.rmangels.filemanager.api.user.dto.UserDto;
import be.rmangels.filemanager.api.user.mapper.PrincipalToUserDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;

import static be.rmangels.filemanager.api.user.UserResource.USER_RESOURCE_URL;

@RequestMapping(USER_RESOURCE_URL)
@RestController
public class UserResource {
    static final String USER_RESOURCE_URL = "/api/users";

    @Inject
    private PrincipalToUserDtoMapper principalToUserDtoMapper;

    @GetMapping
    public UserDto user(Principal principal) {
        return principalToUserDtoMapper.mapToUserDto(principal);
    }
}
