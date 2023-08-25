package apicc.controller;

import apicc.model.dto.ReactionDTO;
import apicc.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping
    public ResponseEntity<ReactionDTO> create(@RequestBody ReactionDTO reactionDTO) {
        return new ResponseEntity<>(this.reactionService.create(reactionDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReactionDTO> delete(@PathVariable int id) {
        return new ResponseEntity<>(this.reactionService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<ReactionDTO>> getAllReactionsForPost(@PathVariable int id) {
        return new ResponseEntity<>(this.reactionService.findAllForPost(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReactionDTO> update(@RequestBody ReactionDTO reactionDTO) {
        return new ResponseEntity<>(this.reactionService.update(reactionDTO), HttpStatus.OK);
    }




//    @GetMapping("/comment/{id}")
//    public ResponseEntity<Set<ReactionDTO>> getAllReactionsForComment(@PathVariable int id) {
//        return new ResponseEntity<>(this.reactionService.findAllForComment(id), HttpStatus.OK);
//    }

}
