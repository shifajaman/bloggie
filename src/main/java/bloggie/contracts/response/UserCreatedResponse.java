package bloggie.contracts.response;

import bloggie.domain.User;
import bloggie.errors.BloggieError;

import java.util.List;

public class UserCreatedResponse {
    private User user;
    private List<BloggieError> errors;

    public UserCreatedResponse() {
    }

    public UserCreatedResponse(User user, List<BloggieError> errors) {
        this.user = user;
        this.errors = errors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BloggieError> getErrors() {
        return errors;
    }

    public void setErrors(List<BloggieError> errors) {
        this.errors = errors;
    }
}
