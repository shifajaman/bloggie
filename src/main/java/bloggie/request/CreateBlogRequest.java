package bloggie.request;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateBlogRequest {
    @NotNull(message = "title cannot be empty")
    @Size(min = 3, max = 255, message = "title needs to be greater than 4 and less than 255")
    private String title;
    @NotNull(message = "body cannot be empty")
    @Size(min = 10, max = 255, message = "body needs to be greater than 10 and less than 255")
    private String body;

    public CreateBlogRequest() {
    }

    public CreateBlogRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "CreateBlogRequest{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
