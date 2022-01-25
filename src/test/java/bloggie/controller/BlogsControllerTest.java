package bloggie.controller;


import bloggie.advice.GlobalControllerAdvice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

    @ExtendWith(MockitoExtension.class)
    class BlogsControllerTest {

        @InjectMocks
        private BlogsController controller;

        private MockMvc mockMvc;

        @BeforeEach
        void setup(){
            mockMvc = MockMvcBuilders.standaloneSetup(controller)
                    .setControllerAdvice(new GlobalControllerAdvice()).build();
        }

        @Test
        @DisplayName("create blog should succeed by returning 2xx status code")
        void createUserShouldSucceed() throws Exception {
            var requestBody =
                """
                { "title":"shifa",
                "body":"anythinggggs"
                }
                """;
            var expectedResp = """
                {"blog":{"title":"shifa","body":"anythinggggs"},"errors":null}
                """;

            var responseBody = mockMvc.perform(createRequest(requestBody))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(expectedResp.trim(), responseBody.trim());
        }

        private MockHttpServletRequestBuilder createRequest(String requestBody) {
            return MockMvcRequestBuilders.post("/v1/blogs/")
                    .content(requestBody).contentType("application/json");
        }

        @Test
        @DisplayName("create blog should fail if the blog request is invalid")
        void createBlogShouldFailForInvalidInput() throws Exception {
            var requestBody =
                """
                {"title":"",
                "body":""}
                """;
            var expectedResp = """
                {"blog":null,"errors":[{"description":"body needs to be greater than 10 and less than 255, title needs to be greater than 4 and less than 255","errorCode":"INVALID_BLOG_INPUT"}]}
                """;
            var responseBody = mockMvc.perform(createRequest(requestBody))
                    .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                    .andReturn().getResponse().getContentAsString();

            assertEquals(expectedResp.trim(), responseBody.trim());
        }
    }

