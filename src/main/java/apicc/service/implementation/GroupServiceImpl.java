package apicc.service.implementation;

import apicc.model.dto.GroupAdminDTO;
import apicc.model.dto.GroupDTO;
import apicc.model.dto.GroupRequestDTO;
import apicc.model.entity.Group;
import apicc.model.entity.GroupAdmin;
import apicc.model.entity.User;
import apicc.repository.GroupRepository;
import apicc.service.GroupAdminService;
import apicc.service.GroupRequestService;
import apicc.service.GroupService;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Lazy
    @Autowired
    private GroupRequestService groupRequestService;
    @Autowired
    private GroupAdminService groupAdminService;

    @Override
    public GroupDTO createGroup(GroupDTO groupDTO) {

        Group group = new Group();
        User user=userService.loggedUser();
        group.setCreationDate(LocalDateTime.now());
        group.setSuspended(false);
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group = groupRepository.save(group);
        group.getAdmins().add(groupAdminService.createEntity(group, user));
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDTO deleteGroup(int id) {
        Group group = this.findOneById(id);
        group.setSuspended(true);
        group = groupRepository.save(group);

        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDTO updateGroup(GroupDTO groupDTO) {
        Group group = this.findOneById(groupDTO.getId());
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        group = groupRepository.save(group);
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDTO findById(int id) {
        return modelMapper.map(this.groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found")), GroupDTO.class);
    }

    @Override
    public List<GroupDTO> findAll() {

        List<GroupDTO> allGroupsDTOs = new ArrayList<>();
        List<Group> allGroups = this.groupRepository.findAll();
        for (Group group :
                allGroups) {
            if(!group.isSuspended()) {
                allGroupsDTOs.add(modelMapper.map(group, GroupDTO.class));
            }
        }
        return allGroupsDTOs;
    }

    @Override
    public List<GroupDTO> findAllByUser(int id) {

        List<GroupDTO> allGroupsDTOs = new ArrayList<>();
        List<Group> allGroups = this.groupRepository.getAllUserGroups(id);
        for (Group group :
                allGroups) {
            allGroupsDTOs.add(modelMapper.map(group, GroupDTO.class));
        }
        return allGroupsDTOs;

    }


    private Group findOneById(int id) {
        Optional<Group> group = this.groupRepository.findById(id);
        if (group.isEmpty()) {
            throw new EntityNotFoundException("Resource not found");
        }
        return group.get();
    }

    @Override
    public GroupAdminDTO createNewAdmin(int groupID, int userID) {
        User user = this.userService.findById(userID);
        Group group = this.groupRepository.findById(groupID).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        GroupAdmin admin = this.groupAdminService.createEntity(group, user);

        return modelMapper.map(admin, GroupAdminDTO.class);
    }

    @Override
    public GroupRequestDTO joinGroup(int id) {
        Group group = this.groupRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Group you were looking for is not found"));

        return this.groupRequestService.save(group);
    }


    @Override
    public List<GroupRequestDTO> getAllPendingMembersForGroup(int id) {


        if (!this.groupAdminService.isLoggedUserAdminInGroup(id)) {
            throw new EntityNotFoundException("You're not admin! Permission denied");
        }

        return this.groupRequestService.findAllRequestsOnPendingByGroupID(id);
    }


    @Override
    public List<GroupRequestDTO> findAllMembersForGroup(int id) {
        return this.groupRequestService.findAllMembersByGroupID(id);
    }


}
