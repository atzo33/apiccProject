package apicc.service.implementation;

import apicc.model.dto.CommentDTO;
import apicc.model.dto.PostDTO;
import apicc.model.dto.UserDTO;
import apicc.model.entity.Comment;
import apicc.model.entity.Post;
import apicc.model.entity.User;
import apicc.repository.CommentsRepository;
import apicc.service.CommentsService;
import apicc.service.PostService;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentsRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;



    @Override
    public CommentDTO save(CommentDTO newCommentDTO) {
        Comment newComment= new Comment();
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setText(newCommentDTO.getText());
        PostDTO post=postService.findById(newCommentDTO.getPostId());
        newComment.setPost(modelMapper.map(post, Post.class));
        User user = userService.loggedUser();
        newComment.setUser(modelMapper.map(user, User.class));

        newComment=commentRepository.save(newComment);

        newCommentDTO.setId(newComment.getId());
        newCommentDTO.setPostedBy(modelMapper.map(user, UserDTO.class));


        return newCommentDTO;
    }

    @Override
    public CommentDTO findOne(Integer id) {
        return  modelMapper.map(commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found")), CommentDTO.class);

    }

    @Override
    public List<CommentDTO> listAllForPost(Integer postID) {
        List<Comment> comments = commentRepository.findAllPostComments(postID);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(Comment comment : comments){
            if (comment.getPost().getId() == postID) {
                CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

                commentDTOS.add(commentDTO);
            }
        }
        return commentDTOS;
    }

    @Override
    public CommentDTO update(CommentDTO updatedComment, int id) {
        Comment comment= commentRepository.findById(id).orElseThrow(()->new RuntimeException());
        if (userService.loggedUser().getId() != comment.getUser().getId()){
            throw new RuntimeException("Unauthorized");
        }
        comment.setText(updatedComment.getText());
        commentRepository.save(comment);
        return modelMapper.map(comment,CommentDTO.class);
    }

    @Override
    public CommentDTO delete(Integer id) {
        Comment comment= commentRepository.findById(id).orElseThrow(()->new RuntimeException());
        if (userService.loggedUser().getId() != comment.getUser().getId()){
            throw new RuntimeException("Unauthorized");
        }
        comment.setDeleted(true);
        commentRepository.save(comment);
        return modelMapper.map(comment,CommentDTO.class);
    }

    @Override
    public List<CommentDTO> findAllRepliesForComment(Integer id) {
        return null;
    }
}
