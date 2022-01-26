package bloggie.controllers;

import bloggie.contracts.request.CreateUserRequest;
import bloggie.contracts.response.UserCreatedResponse;
import bloggie.domain.User;
import bloggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/users")
public class UsersController {
    @Autowired
    private UserService service;

    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreatedResponse create(@RequestBody @Valid  CreateUserRequest request) {
        var user = new User(request.getName());
        var createdUser = service.createUser(user);
        return new UserCreatedResponse(createdUser, null);
    }
}
