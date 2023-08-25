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

import javax.persistence.EntityNotFoundException;
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
        Post post = postRepository.findById(id).orElseThrow(()->new RuntimeException());

        System.out.println(userService.loggedUser().getId());
        if ( userService.loggedUser().getId() != post.getUser().getId()){
            throw new RuntimeException("Unauthorized");
        }
        post.setDeleted(true);
        this.postRepository.save(post);

        return null;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO,int id) {

        Post post = postRepository.findFirstByIdWithCollections(id).orElseThrow(()->new RuntimeException());
        System.out.println(post.getContent());

        if (userService.loggedUser().getId() != post.getUser().getId()){
            throw new RuntimeException("Unauthorized");
        }

        post.setContent(postDTO.getContent());
        postRepository.save(post);

        return modelMapper.map(post, PostDTO.class);
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


        Optional<Post> post = postRepository.findFirstByIdWithCollections(id);
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

    @Override
    public Post findOnePost(int id) {
        return postRepository.findFirstByIdWithCollections(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }
}
