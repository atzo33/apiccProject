package apicc.service.implementation;

import apicc.model.dto.PostDTO;
import apicc.model.dto.ReactionDTO;
import apicc.model.dto.UserDTO;
import apicc.model.entity.EReaction;
import apicc.model.entity.Post;
import apicc.model.entity.Reactions;
import apicc.model.entity.Roles;
import apicc.repository.ReactionRepository;
import apicc.service.PostService;
import apicc.service.ReactionService;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;

    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReactionServiceImpl(ReactionRepository reactionRepository,

                               UserService userService,
                               PostService postService, ModelMapper modelMapper) {
        this.reactionRepository = reactionRepository;

        this.userService = userService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }
    @Override
    public ReactionDTO create(ReactionDTO reactionDTO) {
        Reactions reaction = modelMapper.map(reactionDTO, Reactions.class);
        reaction.setTimeOfReaction(LocalDateTime.now());
        reaction.setReactedBy(userService.loggedUser());
        reaction.setType(reactionDTO.getType());

//        if (reactionDTO.getCommentIdReactedTo() != null) {
//            Comment comment = commentService.findOneEntity( reactionDTO.getCommentIdReactedTo());

//            reaction.setReactedTo(comment);
//        }
        if (reactionDTO.getPostIdReactedTo() != null) {
            Post post = postService.findOnePost(reactionDTO.getPostIdReactedTo());
            reaction.setPostIdReactedTo(post);
        }
        reaction = reactionRepository.save(reaction);
        return modelMapper.map(reaction, ReactionDTO.class);
    }

    @Override
    public ReactionDTO delete(int id) {
        Reactions reaction = reactionRepository.findById(id).orElseThrow(()-> new RuntimeException());
        if(reaction.getReactedBy().getRole() != Roles.ADMIN && userService.loggedUser().getId() != reaction.getReactedBy().getId()){
            throw new RuntimeException("Unauthorized");
        }

        reactionRepository.delete(reaction);
        ReactionDTO reactionDTO = modelMapper.map(reaction,ReactionDTO.class);
        return  reactionDTO;
    }

    @Override
    public ReactionDTO update(ReactionDTO updatedReaction) {
        Reactions reaction = this.reactionRepository.findById(updatedReaction.getId()).orElseThrow(() -> new EntityNotFoundException("Reaction not found"));
        reaction.setType(updatedReaction.getType());
        reaction.setTimeOfReaction(LocalDateTime.now());
        reactionRepository.save(reaction);
        return modelMapper.map(reaction, ReactionDTO.class);
    }

    @Override
    public Set<ReactionDTO> findAllForComment(int id) {
        return this.reactionRepository.findByCommentId(id).stream().map(reaction -> {
            return modelMapper.map(reaction, ReactionDTO.class);
        }).collect(Collectors.toSet());
    }

//    @Override
//    public Set<ReactionDTO> findAllForPost(int id) {
//        return this.reactionRepository.findByPostId(id).stream().map(reaction -> modelMapper.map(reaction, ReactionDTO.class)).collect(Collectors.toSet());
//    }

    @Override
    public List<ReactionDTO>findAllForPost(int id){
    List<Reactions> reactions = reactionRepository.findByPostId(id);
    List<ReactionDTO> reactionDTOS = new ArrayList<>();
        for(Reactions reaction : reactions){
        if (reaction.getPostIdReactedTo().getId() == id) {
            ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);

            reactionDTOS.add(reactionDTO);
        }
    }
        return reactionDTOS;

    }

//    @Override
//    public FindOneForDeletion(int id, EReaction type){
//        List<Reactions> reaction = reactionRepository.findByPostId(id);
//        List<ReactionDTO> reactionDTOS = new ArrayList<>();
//        for(Reactions reaction : reactions){
//            if (reaction.getPostIdReactedTo().getId() == id) {
//                ReactionDTO reactionDTO = modelMapper.map(reaction, ReactionDTO.class);
//
//                reactionDTOS.add(reactionDTO);
//            }
//        }
//        return reactionDTOS;
//
//    }
}
