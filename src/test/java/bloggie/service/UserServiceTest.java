package bloggie.service;
import bloggie.domain.User;
import bloggie.errors.InternalServerException;
import bloggie.errors.InvalidDataException;
import bloggie.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("should create a user on success")
    public void shouldCreateAUser() {
        UserService service = new UserService(userRepository);
        var inputUser = new User("shifa");
        var expectedUser = new User(1, "shifa");
        when(userRepository.save(inputUser)).thenReturn(expectedUser);

        var output = service.createUser(inputUser);

        Assertions.assertEquals(expectedUser, output);
    }

    @Test
    @DisplayName("should not create a user on failure")
    public void shouldNotCreateUser() {
        UserService service = new UserService(userRepository);
        var inputUser = new User("shifa");

        when(userRepository.save(inputUser)).thenThrow(DataIntegrityViolationException.class);

        var exception = assertThrows(InvalidDataException.class, () -> service.createUser(inputUser));
        Assertions.assertEquals("user name shifa is already taken", exception.getMessage());
    }

    @Test
    @DisplayName("should find all user on success")
    public void shouldFindAllUser() {
        UserService service = new UserService(userRepository);
        User user1 = new User(1, "shifa");
        User user2 = new User(2, "zeeshan");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(userRepository.findAll()).thenReturn(users);

        var output = service.findAllUser();
        Assertions.assertEquals(users, output);
    }

    @Test
    @DisplayName("should not find any user on failure")
    public void shouldNotFindAnyUser() {
        UserService service = new UserService(userRepository);

        when(userRepository.findAll()).thenThrow(new InternalServerException("something went wrong", null));

        var exception = assertThrows(InternalServerException.class, () -> service.findAllUser());
        Assertions.assertEquals("something went wrong", exception.getMessage());
    }

    @Test
    @DisplayName("ShouldFindUserWithGivenId")
    void shouldFindUserWithGivenId() {
        UserService service = new UserService(userRepository);
        User user = new User(1, "shifa");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<User> actual = service.findById("1");


        var expected = service.findById("1");
        assertThat(actual).isSameAs(expected);
    }

    @Test
    @DisplayName("ShouldNotFindUserWithGivenId")
    void ShouldNotFindUserWithGivenId() {
        UserService service = new UserService(userRepository);
        doReturn(Optional.empty()).when(userRepository).findById("1");
        Optional<User> returnedUser = service.findById("1");
        Assertions.assertFalse(returnedUser.isPresent(), "User should not be found");
    }

}
