package bloggie.service;

import bloggie.domain.User;
import bloggie.errors.InternalServerException;
import bloggie.errors.InvalidDataException;
import bloggie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidDataException(String.format("user name %s is already taken", user.getName()), e);
        }
    }
    public List<User> findAllUser() throws InvalidDataException {
        try{
            return repository.findAll();
        }catch (Exception e){
            throw new InternalServerException("something went wrong",e);
        }
    }

    public Optional<User> findById(String id) {
        try{
            return repository.findById(id);
        }catch (Exception e){
            throw new InternalServerException("list of user with given id",e);
        }
    }
}
