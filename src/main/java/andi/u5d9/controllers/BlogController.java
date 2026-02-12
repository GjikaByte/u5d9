package andi.u5d9.controllers;

import andi.u5d9.entities.Autore;
import andi.u5d9.entities.Blog;
import andi.u5d9.payloads.AutorePayload;
import andi.u5d9.payloads.BlogPayload;
import andi.u5d9.services.AutoreService;
import andi.u5d9.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("blogPosts")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // 1. POST http://localhost:3001/blogPosts (+ Payload)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Blog createBlog(@RequestBody BlogPayload payload) {
        return this.blogService.save(payload);
    }

    // 2. GET http://localhost:3001/blogPosts
    @GetMapping
    public Page<Blog> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "titolo") String orderBy,
                              @RequestParam(defaultValue = "asc") String sortCriteria) {

        return this.blogService.findAll(page, size, orderBy, sortCriteria);
    }

    // 3. GET http://localhost:3001/blogPosts/{blogId}
    @GetMapping("/{blogId}")
    public Blog findById(@PathVariable UUID blogId) {
        return this.blogService.findById(blogId);
    }

    // 4. PUT http://localhost:3001/blogPosts/{blogId}
    @PutMapping("/{blogId}")
    public Blog findByIdAndUpdate(@PathVariable UUID blogId, @RequestBody BlogPayload payload) {
        return this.blogService.findByIdAndUpdate(blogId, payload);
    }

    // 5. DELETE http://localhost:3001/blogPosts/{blogId}
    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID blogId) {
        this.blogService.findByIdAndDelete(blogId);
    }

}
