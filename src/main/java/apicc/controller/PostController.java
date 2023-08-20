package apicc.controller;

import apicc.model.dto.PostDTO;
import apicc.model.entity.Post;
import apicc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/add")
    public ResponseEntity<PostDTO> create(@RequestBody @Validated PostDTO newPost){

        return new ResponseEntity<>(this.postService.newPost(newPost), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> findAllPostsByUser(@PathVariable int userId) {

        List<PostDTO> postsByUser = postService.findAllByUser(userId);

        if (postsByUser.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(postsByUser);
    }

}
