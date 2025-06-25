package com.lsm.ws.user.context.auth

import com.lsm.ws.user.configuration.exception.InvalidCredentialsException
import com.lsm.ws.user.configuration.exception.UserAlreadyExistsException
import com.lsm.ws.user.context.auth.dto.LoginRequest
import com.lsm.ws.user.context.auth.dto.RegisterRequest
import com.lsm.ws.user.domain.cache.JwtBlacklistStore
import com.lsm.ws.user.domain.user.User
import com.lsm.ws.user.domain.user.UserRepository
import com.lsm.ws.user.infrastructure.jwt.JwtService
import com.lsm.ws.user.infrastructure.jwt.TokenPair
import com.lsm.ws.user.infrastructure.rest.context.RequestContext
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.time.LocalDate

class AuthServiceSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def passwordEncoder = Mock(PasswordEncoder)
    def jwtService = Mock(JwtService)
    def requestContext = Mock(RequestContext)
    def jwtBlacklistStore = Mock(JwtBlacklistStore)

    def authService = new AuthService(
            userRepository,
            passwordEncoder,
            jwtService,
            requestContext,
            jwtBlacklistStore
    )

    def "should register new user successfully"() {
        given:
        def request = new RegisterRequest("john@example.com", "password123", "John", "Doe", LocalDate.now().minusYears(20))
        userRepository.findByEmail("john@example.com") >> Optional.empty()
        passwordEncoder.encode("password123") >> "hashed"
        jwtService.generateUserWebToken(_) >> new TokenPair("access", "refresh")

        when:
        def result = authService.register(request)

        then:
        1 * userRepository.save(_ as User) >> { args -> args[0] }
        result.accessToken() == "access"
        result.refreshToken() == "refresh"
    }

    def "should throw exception when user with email already exists"() {
        given:
        def existingUser = Mock(User)
        def request = new RegisterRequest("john@example.com", "password123", "John", "Doe", LocalDate.now().minusYears(20))
        userRepository.findByEmail(_) >> Optional.of(existingUser)

        when:
        authService.register(request)

        then:
        thrown(UserAlreadyExistsException)
    }

    def "should login with valid credentials"() {
        given:
        def loginRequest = new LoginRequest("john@example.com", "password123")
        def user = Mock(User)
        user.password() >> "hashed".bytes
        userRepository.findByEmail("john@example.com") >> Optional.of(user)
        passwordEncoder.matches("password123", "hashed") >> true
        jwtService.generateUserWebToken(_) >> new TokenPair("access", "refresh")

        when:
        def result = authService.login(loginRequest)

        then:
        result.accessToken() == "access"
        result.refreshToken() == "refresh"
    }

    def "should throw exception when login password is incorrect"() {
        given:
        def loginRequest = new LoginRequest("john@example.com", "wrongpass")
        def user = Mock(User)
        user.password() >> "hashed".bytes
        userRepository.findByEmail("john@example.com") >> Optional.of(user)
        passwordEncoder.matches("wrongpass", "hashed") >> false

        when:
        authService.login(loginRequest)

        then:
        thrown(InvalidCredentialsException)
    }

    def "should throw exception when user not found during login"() {
        given:
        def loginRequest = new LoginRequest("not@found.com", "pass")
        userRepository.findByEmail("not@found.com") >> Optional.empty()

        when:
        authService.login(loginRequest)

        then:
        thrown(InvalidCredentialsException)
    }

    def "should refresh token"() {
        given:
        requestContext.originalToken() >> "old"
        jwtService.refreshToken("old") >> new TokenPair("access", "refresh")

        when:
        def result = authService.refresh()

        then:
        result.accessToken() == "access"
        result.refreshToken() == "refresh"
    }

    def "should blacklist token on logout"() {
        given:
        requestContext.originalToken() >> "tokenToBlacklist"

        when:
        authService.logout()

        then:
        1 * jwtBlacklistStore.put("tokenToBlacklist")
    }
}
