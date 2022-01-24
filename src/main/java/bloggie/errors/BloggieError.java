package bloggie.errors;

public class BloggieError {
    private String description;
    private String errorCode;

    public BloggieError(){}

    public BloggieError(String description, String errorCode) {
        this.description = description;
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
