package apicc.service;

import apicc.model.dto.PostDTO;
import apicc.model.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDTO newPost(PostDTO newPostDTO);




    PostDTO deletePost(int id);

    PostDTO updatePost(PostDTO newPostDTO, int id);



    List<PostDTO> findAll();

    PostDTO findById(int id);

    List<PostDTO> findAllByUser(int id);

    Post findOnePost(int id);
}
