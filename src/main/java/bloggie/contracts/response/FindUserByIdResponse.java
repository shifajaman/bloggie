package bloggie.contracts.response;

import bloggie.domain.User;
import bloggie.errors.BloggieError;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FindUserByIdResponse {
    private User  user;
    private List<BloggieError> errors;


    public FindUserByIdResponse(User user, List<BloggieError> errors) {
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
        if (!(o instanceof bloggie.contracts.response.UserCreatedResponse)) return false;
        bloggie.contracts.response.UserCreatedResponse that = (bloggie.contracts.response.UserCreatedResponse) o;
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


