package com.lsm.ws.user.context.info;

import com.lsm.ws.user.configuration.exception.UserDontExistException;
import com.lsm.ws.user.context.info.dto.UserInfoDto;
import com.lsm.ws.user.domain.user.UserRepository;
import com.lsm.ws.user.infrastructure.rest.context.RequestContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user/")
@Tag(name = "Informacje o użytkownikach")
public class UserInfoEndpoint {

    private static final String INFO = "User information";
    private static final String INFO_DESC = "returns logged user information";

    private final UserRepository userRepository;
    private final RequestContext requestContext;

    public UserInfoEndpoint(UserRepository userRepository, RequestContext requestContext) {
        this.userRepository = userRepository;
        this.requestContext = requestContext;
    }

    @Operation(summary = "Informacje o zalogowanym użytkowniku",
            description = "Zwraca informacje o zalogowanym użytkowniku")
    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> info() {
        var user = userRepository.findById(requestContext.userId())
                                 .orElseThrow(UserDontExistException::new);

        return ResponseEntity.ok(UserInfoDto.from(user));
    }

    @Operation(summary = "Informacje o użytkowniku", description = "Zwraca informacje o danym użytkowniku")
    @GetMapping("/{userId}/info")
    public ResponseEntity<UserInfoDto> info(@PathVariable String userId) {
        var user = userRepository.findById(userId)
                                 .orElseThrow(UserDontExistException::new);

        return ResponseEntity.ok(UserInfoDto.from(user));
    }
}
