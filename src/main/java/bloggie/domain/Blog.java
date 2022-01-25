package bloggie.domain;

public class Blog {
private String title;
private String body;
    public Blog(){}
    public Blog(String title,String body){
            this.title=title;
            this.body=body;
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
        return "Blog{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}



