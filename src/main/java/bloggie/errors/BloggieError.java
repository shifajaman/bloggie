package bloggie.errors;

public class BloggieError {
    private String description;
    private ErrorCodes errorCode;

    public BloggieError(String description, ErrorCodes errorCode) {
        this.description = description;
        this.errorCode = errorCode;
    }
}
