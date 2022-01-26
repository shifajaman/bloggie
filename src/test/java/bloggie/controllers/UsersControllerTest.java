package bloggie.controllers;

import bloggie.controllers.advice.GlobalControllerAdvice;
import bloggie.domain.User;
import bloggie.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController controller;
    @Mock
    private UserService service;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
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

        Mockito.when(service.createUser(inputUser)).thenReturn(createdUser);

        var responseBody = mockMvc.perform(createRequest(requestBody))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
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
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedResp.trim(), responseBody.trim());
    }
}