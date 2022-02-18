package bloggie.controllers;

import bloggie.errors.InternalServerException;
import bloggie.contracts.response.UserCreatedResponse;
import bloggie.controllers.advice.GlobalControllerAdvice;
import bloggie.domain.User;
import bloggie.errors.InvalidDataException;
import bloggie.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController controller;
    @Mock
    private UserService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalControllerAdvice()).build();
    }

    @Test
    @DisplayName("create user should succeed by returning 2xx status code")
    void createUserShouldSucceed() throws Exception {
        var requestBody = """
                {"name":"shifa"}
                """;
        var expectedResp = """                                    
                {"user":{"id":1,"name":"shifa"},"errors":null}
                """;
        var inputUser = new User("shifa");
        var createdUser = new User(1, "shifa");

        when(service.createUser(inputUser)).thenReturn(createdUser);

        ResultActions result = mockMvc.perform(createRequest(requestBody));
        var responseBody = result
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedResp.trim(), responseBody.trim());
    }

    @Test
    @DisplayName("create user should fail by returning 4xx status code for existing user name")
    void createUserShouldFailForExistingUserName() throws Exception {
        var requestBody = """
                {"name":"shifa"}
                """;
        var expectedResp = """
                 {"user":null,"errors":[{"description":null,"errorCode":"INVALID_USER_NAME"}]}
                """;
        var inputUser = new User("shifa");

        when(service.createUser(inputUser)).thenThrow(InvalidDataException.class);

        var responseBody = mockMvc.perform(createRequest(requestBody))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedResp.trim(), responseBody.trim());
    }

    private MockHttpServletRequestBuilder createRequest(String requestBody) {
        return MockMvcRequestBuilders.post("/v1/users/").content(requestBody).contentType("application/json");
    }


    @Test
    @DisplayName("create user should fail if the user name is not present")
    void createUserShouldFailForInvalidName() throws Exception {
        var requestBody = """
                {"name":""}
                """;
        var expectedResp = """
                {"user":null,"errors":[{"description":"name needs to be greater than 4 and less than 255","errorCode":"INVALID_USER_NAME"}]}
                """;
        var responseBody = mockMvc.perform(createRequest(requestBody))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertEquals(createdResponseObj(expectedResp.trim()), createdResponseObj(responseBody.trim()));
    }

    private UserCreatedResponse createdResponseObj(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseBody, UserCreatedResponse.class);
    }

    @Test
    @DisplayName("should return list of users on success")
    void shouldReturnTheListOfUsers() throws Exception {
        User user1 = new User(1, "shifa");
        User user2 = new User(2, "zeeshan");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        when(service.findAllUser()).thenReturn(users);
        var result = mockMvc.perform(getAllUserReq()
                        .contentType("application/json"))
                .andExpect(status().isOk());

        String expectedRespBody = """
                {"user":[{"id":1,"name":"shifa"},{"id":2,"name":"zeeshan"}],"errors":null}
                """;
        assertEquals(expectedRespBody.trim(), result.andReturn().getResponse().getContentAsString());
    }


    @Test
    @DisplayName("should not return the list of users on failure")
    void shouldNotReturnTheListOfUsers() throws Exception {
        when(service.findAllUser()).thenThrow(new InternalServerException("something went wrong", null));
        var result = mockMvc.perform(getAllUserReq()
                        .contentType("application/json"))
                .andExpect(status().is5xxServerError());

        String expectedRespBody = """
                {"user":null,"errors":[{"description":"something went wrong","errorCode":"INTERNAL_SERVER_ERROR"}]}
                """;
        assertEquals(expectedRespBody.trim(), result.andReturn().getResponse().getContentAsString());
    }

    private MockHttpServletRequestBuilder getAllUserReq() {
        return MockMvcRequestBuilders.get("/v1/users/").contentType("application/json");
    }

    @Test
    @DisplayName("should return user with id on success")
    void shouldReturnUserWithId() throws Exception {
        User user1 = new User(1, "shifa");
        when(service.findById("1")).thenReturn(Optional.of(user1));
        var result = mockMvc.perform(findUserByIdReq()
                        .contentType("application/json"))
                .andExpect(status().isOk());


    }

    private MockHttpServletRequestBuilder findUserByIdReq() {
        return MockMvcRequestBuilders.get("/v1/users/1").param("id", "1").contentType("application/json");
    }

    @Test
    @DisplayName("Should not return user with given id ")
    void shouldNotreturnUserWithId() throws Exception {
        when(service.findById("1")).thenReturn(Optional.empty());
        var result = mockMvc.perform(findUserByIdReq()
                        .contentType("application/json"))
                .andExpect(status().is4xxClientError());

    }

}
