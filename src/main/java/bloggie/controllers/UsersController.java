package bloggie.controllers;

import bloggie.contracts.request.CreateUserRequest;
import bloggie.contracts.response.FindUserByIdResponse;
import bloggie.contracts.response.GetAllUserResponse;
import bloggie.contracts.response.UserCreatedResponse;
import bloggie.domain.User;
import bloggie.errors.InvalidDataException;
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
    public UserCreatedResponse create(@RequestBody @Valid CreateUserRequest request) {
        var user = new User(request.getName());
        var createdUser = service.createUser(user);
        return new UserCreatedResponse(createdUser, null);
    }

    @GetMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public GetAllUserResponse getAll() {
        var allUsers = service.findAllUser();
        return new GetAllUserResponse(allUsers, null);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FindUserByIdResponse findById(@RequestParam("id") String id) {
        var user = service.findById(id);
        if (user.isPresent())
        {
            return new FindUserByIdResponse(user.get(), null);
        }
        throw new InvalidDataException("the user id "+ id+" does not exist",null);
    }

}
