package bloggie.contracts.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequest {
    @NotNull(message = "name cannot be empty")
    @Size(min = 3, max = 255, message = "name needs to be greater than 4 and less than 255")
    private String name;
    public CreateUserRequest() {}
    public CreateUserRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
