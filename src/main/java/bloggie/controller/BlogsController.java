package bloggie.controller;

import bloggie.domain.Blog;
import bloggie.request.CreateBlogRequest;
import bloggie.response.BlogCreatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/blogs")
public class BlogsController {
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public BlogCreatedResponse create(@RequestBody @Valid CreateBlogRequest request){
        var blog=new Blog(request.getTitle(), request.getBody());
        return new BlogCreatedResponse(blog,null);
    }
}
