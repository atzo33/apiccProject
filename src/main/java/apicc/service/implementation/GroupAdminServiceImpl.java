package apicc.service.implementation;

import apicc.model.dto.GroupAdminDTO;
import apicc.model.entity.Group;
import apicc.model.entity.GroupAdmin;
import apicc.model.entity.User;
import apicc.repository.GroupAdminRepository;
import apicc.service.GroupAdminService;
import apicc.service.GroupRequestService;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupAdminServiceImpl implements GroupAdminService {



    @Autowired
    private GroupAdminRepository groupAdminRepository;
    @Autowired
    private GroupRequestService groupRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public GroupAdmin createEntity(Group group,User user) {

        GroupAdmin groupAdmin = new GroupAdmin();

        groupAdmin.setAdminAt(group);

        groupAdmin.setUser(user);

        groupAdmin.setDeleted(false);

        groupAdmin = groupAdminRepository.save(groupAdmin);

        return groupAdmin;
    }

    @Override
    public GroupAdmin delete(int id) {

        GroupAdmin groupAdmin = this.groupAdminRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found"));

        groupAdmin.setDeleted(true);

        this.groupAdminRepository.save(groupAdmin);

        return groupAdmin;
    }

    @Override
    public List<GroupAdminDTO> findAllByGroupID(Integer id) {

        List <GroupAdmin> admins=groupAdminRepository.findAllByGroupID(id);
        List<GroupAdminDTO> adminDTOS = new ArrayList<>();

        for (GroupAdmin admin : admins) {
            GroupAdminDTO adminDTO = modelMapper.map(admin, GroupAdminDTO.class);
            adminDTOS.add(adminDTO);
        }

        return adminDTOS;
    }

    @Override
    public boolean isLoggedUserAdminInGroup(int groupID) {

        User user = this.userService.loggedUser();
        Optional<GroupAdmin> foundAdmin = this.groupAdminRepository.findByUserIDAndGroupID(groupID, user.getId());

        return foundAdmin.isPresent();
    }

    @Override
    public List<GroupAdminDTO> findAllWhereUserIsAdmin(int userID) {

        return groupAdminRepository.findAllByUserID(userID)
                .stream()
                .map(groupAdmin -> {
                    GroupAdminDTO groupAdminDTO = new GroupAdminDTO();
                    BeanUtils.copyProperties(groupAdmin, groupAdminDTO);
                    groupAdminDTO.setGroupID(groupAdmin.getAdminAt().getId());
                    return groupAdminDTO;
                })
                .collect(Collectors.toList());
    }
}
