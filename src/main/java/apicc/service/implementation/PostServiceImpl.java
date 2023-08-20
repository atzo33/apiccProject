package apicc.service.implementation;

import apicc.model.dto.PostDTO;
import apicc.model.dto.UserDTO;
import apicc.model.entity.Post;
import apicc.model.entity.User;
import apicc.repository.PostRepository;
import apicc.service.PostService;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDTO newPost(PostDTO newPostDTO) {

        Post newPost = new Post();

        newPost.setCreationDate(LocalDateTime.now());

        newPost.setContent(newPostDTO.getContent());

        User user = userService.loggedUser();
        newPost.setUser(user);

        newPost = postRepository.save(newPost);

        newPostDTO.setId(newPost.getId());

        newPostDTO.setUser(modelMapper.map(user,UserDTO.class));

        return newPostDTO;

    }





    @Override
    public PostDTO deletePost(int id) {

        this.postRepository.deleteById(id);

        return null;
    }

    @Override
    public PostDTO updatePost(PostDTO newPostDTO) {

        return null;
    }

    @Override
    public List<PostDTO> findAll() {



        List<Post> posts = postRepository.findAll();

        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    @Override
    public PostDTO findById(int id) {


        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {

            return null;

        }

        Post postObject = post.get();

        PostDTO postDTO = modelMapper.map(postObject, PostDTO.class);

        return postDTO;
    }

    @Override
    public List<PostDTO> findAllByUser(int id) {
        List<Post> posts = postRepository.findAllPostsByUser(id);
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post post : posts){
            if (post.getUser().getId() == id) {
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                postDTO.setUser(modelMapper.map(post.getUser(),UserDTO.class));
                postDTOS.add(postDTO);
            }
        }
        return postDTOS;
    }
}
