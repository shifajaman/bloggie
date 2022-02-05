package bloggie.errors;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BloggieError)) return false;
        BloggieError that = (BloggieError) o;
        return Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getErrorCode(), that.getErrorCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDescription(), getErrorCode());
    }

    @Override
    public String toString() {
        return "BloggieError{" +
                "description='" + description + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
