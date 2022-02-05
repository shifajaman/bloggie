package bloggie.contracts.response;

import bloggie.domain.User;
import bloggie.errors.BloggieError;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCreatedResponse)) return false;
        UserCreatedResponse that = (UserCreatedResponse) o;
        return Objects.equals(getUser(), that.getUser()) && Objects.equals(getErrors(), that.getErrors());
    }

    @Override
    public String toString() {
        return "UserCreatedResponse{" +
                "user=" + user +
                ", errors=" + errors +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getErrors());
    }
}
