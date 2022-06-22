package zw.co.macheyo.mhuricore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.macheyo.mhuricore.exception.BadRequestException;
import zw.co.macheyo.mhuricore.exception.ResourceNotFoundException;
import zw.co.macheyo.mhuricore.enums.AuthProvider;
import zw.co.macheyo.mhuricore.model.User;
import zw.co.macheyo.mhuricore.common.ApiResponse;
import zw.co.macheyo.mhuricore.common.AuthResponse;
import zw.co.macheyo.mhuricore.common.LoginRequest;
import zw.co.macheyo.mhuricore.common.SignUpRequest;
import zw.co.macheyo.mhuricore.service.user.UserRepository;
import zw.co.macheyo.mhuricore.service.usergroup.UserGroupService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sign in successful", AuthResponse.builder().accessToken(token).tokenType("Bearer").build()));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        Boolean existsByEmail = userRepository.existsByEmail(signUpRequest.getEmail());
        if(Boolean.TRUE.equals(existsByEmail)) {
            throw new BadRequestException("Email address already in use.");
        }
        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setProvider(AuthProvider.LOCAL);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUserGroup(userGroupService.findById(1L).orElseThrow(()->new ResourceNotFoundException("User group","id",1L)));
        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse<>(true, "User registered successfully",result));
    }

}
