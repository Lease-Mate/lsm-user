package com.lsm.ws.user.context.info;

import com.lsm.ws.user.configuration.exception.UserDontExistException;
import com.lsm.ws.user.context.auth.AuthService;
import com.lsm.ws.user.domain.user.UserRepository;
import com.lsm.ws.user.infrastructure.rest.context.RequestContext;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final RequestContext requestContext;

    public UserService(UserRepository userRepository, AuthService authService, RequestContext requestContext) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.requestContext = requestContext;
    }

    public void delete() {
        var user = userRepository.findById(requestContext.userId())
                                 .orElseThrow(UserDontExistException::new);
        userRepository.delete(user.id());
        authService.logout();
    }
}
