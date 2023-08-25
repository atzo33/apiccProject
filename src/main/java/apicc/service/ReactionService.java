package apicc.service;

import apicc.model.dto.ReactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface ReactionService {
    ReactionDTO create(ReactionDTO reactionDTO);
    ReactionDTO delete(int id);
    ReactionDTO update(ReactionDTO updatedReaction);
    Set<ReactionDTO> findAllForComment(int id);
    List<ReactionDTO> findAllForPost(int id);
}
