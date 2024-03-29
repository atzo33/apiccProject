package apicc.controller;

import apicc.model.dto.CommentDTO;
import apicc.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    @Autowired
    private CommentsService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> save(@RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(this.commentService.save(commentDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getOne(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDTO>> listAllForPost(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.listAllForPost(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> update(@PathVariable Integer id, @RequestBody CommentDTO updatedComment) {
        return new ResponseEntity<>(this.commentService.update(updatedComment,id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommentDTO> delete(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<CommentDTO>> getAllReplies(@PathVariable Integer id) {
        return new ResponseEntity<>(this.commentService.findAllRepliesForComment(id), HttpStatus.OK);
    }
}
