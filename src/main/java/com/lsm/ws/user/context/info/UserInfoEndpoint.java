package com.lsm.ws.user.context.info;

import com.lsm.ws.user.configuration.exception.UserDontExistException;
import com.lsm.ws.user.context.info.dto.UserInfoDto;
import com.lsm.ws.user.domain.user.UserRepository;
import com.lsm.ws.user.infrastructure.rest.context.RequestContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/user/")
@Tag(name = "Offer services")
public class UserInfoEndpoint {

    private static final String INFO = "User information";
    private static final String INFO_DESC = "returns logged user information";

    private final UserRepository userRepository;
    private final RequestContext requestContext;

    public UserInfoEndpoint(UserRepository userRepository, RequestContext requestContext) {
        this.userRepository = userRepository;
        this.requestContext = requestContext;
    }

    @Operation(summary = INFO, description = INFO_DESC)
    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> info() {
        var user = userRepository.findById(requestContext.userId())
                                 .orElseThrow(UserDontExistException::new);

        return ResponseEntity.ok(UserInfoDto.from(user));
    }

}
