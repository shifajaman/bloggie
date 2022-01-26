package bloggie.service;

import bloggie.domain.User;
import bloggie.errors.InvalidDataException;
import bloggie.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("should create a user on success")
    public void shouldCreateAUser() {
        UserService service = new UserService(userRepository);
        var inputUser = new User("shifa");
        var createdUser = new User(1, "shifa");
        Mockito.when(userRepository.save(inputUser)).thenReturn(createdUser);

        var actual = service.createUser(inputUser);

        Assertions.assertEquals(actual, createdUser);
    }

    @Test
    @DisplayName("should not create a user on failure")
    public void shouldNotCreateUser() {
        UserService service = new UserService(userRepository);
        var inputUser = new User("shifa");

        Mockito.when(userRepository.save(inputUser)).thenThrow(DataIntegrityViolationException.class);

        var exception= Assertions.assertThrows(InvalidDataException.class, () -> service.createUser(inputUser));
        Assertions.assertEquals("user name shifa is already taken", exception.getMessage());
    }
}