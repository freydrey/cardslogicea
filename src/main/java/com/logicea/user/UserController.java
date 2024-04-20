package com.logicea.user;


import com.logicea.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    public UserController(UserService userService, JWTUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegistrationRequest userRegistrationRequest){
        userService.addUser(userRegistrationRequest);
        String jwtToken = jwtUtil.issueToken(userRegistrationRequest.email(), userRegistrationRequest.role());

        UserGeneralResponse userGeneralResponse = new UserGeneralResponse(
                HttpStatus.OK.value(),
                "User created successfully"
        );

        return ResponseEntity.ok()
                .body(userGeneralResponse);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer customerId){
         userService.deleteCustomerById(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") Integer customerId,
            @RequestBody UserUpdateRequest userUpdateRequest){
         userService.updateCustomer(customerId,userUpdateRequest);
    }

}
