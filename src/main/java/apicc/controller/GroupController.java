package apicc.controller;

import apicc.model.dto.GroupAdminDTO;
import apicc.model.dto.GroupDTO;
import apicc.model.dto.GroupRequestDTO;
import apicc.model.entity.Group;
import apicc.model.entity.User;
import apicc.service.GroupAdminService;
import apicc.service.GroupRequestService;
import apicc.service.GroupService;
import apicc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
public class GroupController {


    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupAdminService groupAdminService;

    @Autowired
    UserService userService;

    @Autowired
    private GroupRequestService groupRequestService;

    @PostMapping
    public ResponseEntity<GroupDTO> save(@RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(this.groupService.createGroup(groupDTO), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> findOne(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAll() {
        return new ResponseEntity<>(this.groupService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> update(@RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(this.groupService.updateGroup(groupDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GroupDTO> delete(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.deleteGroup(id), HttpStatus.OK);

    }



    @PostMapping("/join/{id}")
    public ResponseEntity<GroupRequestDTO> joinGroup(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.joinGroup(id), HttpStatus.OK);
    }

    @GetMapping("/members/pending/{id}")
    public ResponseEntity<List<GroupRequestDTO>> getAllPendingMembersForGroup(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.getAllPendingMembersForGroup(id), HttpStatus.OK);
    }

    @PostMapping("/members/update-join-request")
    public ResponseEntity<GroupRequestDTO> approveMemberJoin(@RequestBody GroupRequestDTO approveGroupJoinDTO) {
        return new ResponseEntity<>(this.groupRequestService.update(approveGroupJoinDTO.isApproved(),approveGroupJoinDTO.getId()), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<GroupDTO>> getAllUserGroups(@PathVariable int id) {
        return new ResponseEntity<>(this.groupService.findAllByUser(id), HttpStatus.OK);
    }
}
