package apicc.service.implementation;

import apicc.model.dto.GroupDTO;
import apicc.model.dto.GroupRequestDTO;
import apicc.model.dto.PostDTO;
import apicc.model.dto.UserDTO;
import apicc.model.entity.Group;
import apicc.model.entity.GroupRequest;
import apicc.model.entity.Post;
import apicc.model.entity.User;
import apicc.repository.GroupRequestRepository;
import apicc.service.GroupRequestService;
import apicc.service.GroupService;
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
public class GroupRequestServiceImpl implements GroupRequestService {


    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GroupRequestRepository groupRequestRepository;

    @Override
    public GroupRequestDTO save(Group group) {

        User user = this.userService.loggedUser();
        Optional<GroupRequest> doesRequestExist = this.groupRequestRepository.findFirstByUserIDAndGroupID(user.getId(), group.getId());
        if (doesRequestExist.isPresent()) {
            throw new RuntimeException("Already in group!");
        }
        GroupRequest groupRequest = new GroupRequest();

        groupRequest.setGroupTarget(group);

        groupRequest.setUserRequesting(user);

        groupRequest.setCreatedAt(LocalDateTime.now());

        groupRequest.setApproved(null);

        groupRequest = groupRequestRepository.save(groupRequest);

        return modelMapper.map(groupRequest, GroupRequestDTO.class);
    }

    @Override
    public GroupRequestDTO saveForUser(Group group, int userID) {
        User user = this.userService.findById(userID);
        User userForSave=modelMapper.map(user, User.class);
        GroupRequest groupRequest =  new GroupRequest();
        groupRequest.setGroupTarget(group);
        groupRequest.setUserRequesting(userForSave);
        groupRequest.setCreatedAt(LocalDateTime.now());
        groupRequest.setApproved(null);
        groupRequest = groupRequestRepository.save(groupRequest);

        return modelMapper.map(groupRequest, GroupRequestDTO.class);
    }

    @Override
    public List<GroupRequestDTO> findAllMembersByGroupID(int id) {
        List <GroupRequest> requests=groupRequestRepository.findAllByApprovedAndGroupID(id);
        List<GroupRequestDTO> requestDTOS = new ArrayList<>();

        for (GroupRequest request : requests) {
            GroupRequestDTO requestDTO = modelMapper.map(request, GroupRequestDTO.class);
            requestDTOS.add(requestDTO);
        }

        return requestDTOS;

    }

    @Override
    public GroupRequestDTO update(boolean updateStatus, int groupRequestID) {

        GroupRequest groupRequest = this.groupRequestRepository.findFirstById(groupRequestID).orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        groupRequest.setApproved(updateStatus);

        groupRequest.setAt(LocalDateTime.now());

        groupRequest = groupRequestRepository.save(groupRequest);

        return modelMapper.map(groupRequest, GroupRequestDTO.class);

    }

    @Override
    public List<GroupRequestDTO> findAllRequestsOnPendingByGroupID(int id) {

        List <GroupRequest> requests=groupRequestRepository.findAllByPendingAndGroupID(id);
        List<GroupRequestDTO> requestDTOS = new ArrayList<>();

        for (GroupRequest request : requests) {
            GroupRequestDTO requestDTO = modelMapper.map(request, GroupRequestDTO.class);
            requestDTOS.add(requestDTO);
        }

        return requestDTOS;

    }
}
