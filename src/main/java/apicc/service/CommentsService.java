package apicc.service;

import apicc.model.dto.CommentDTO;
import apicc.model.entity.Comment;

import java.util.List;

public interface CommentsService {

    CommentDTO save(CommentDTO newComment);

    CommentDTO findOne(Integer id);


    List<CommentDTO> listAllForPost(Integer postID);



    CommentDTO update(CommentDTO updatedComment, int id);

    CommentDTO delete(Integer id);

    List<CommentDTO> findAllRepliesForComment(Integer id);
}
