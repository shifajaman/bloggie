package bloggie.response;

import bloggie.domain.Blog;
import bloggie.error.BloggieError;

import java.util.List;

public class BlogCreatedResponse {
    private Blog blog;
    private List<BloggieError> errors;

    public BlogCreatedResponse() {
    }

    public BlogCreatedResponse(Blog blog, List<BloggieError> errors) {
        this.blog=blog;
        this.errors = errors;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List<BloggieError> getErrors() {
        return errors;
    }

    public void setErrors(List<BloggieError> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "BlogCreatedResponse{" +
                "blog=" + blog +
                ", error=" + errors +
                '}';
    }
}
