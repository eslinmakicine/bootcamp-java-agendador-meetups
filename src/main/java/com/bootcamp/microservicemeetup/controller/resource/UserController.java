package com.bootcamp.microservicemeetup.controller.resource;

import com.bootcamp.microservicemeetup.controller.dto.UserDTO;
import com.bootcamp.microservicemeetup.model.entity.User;
import com.bootcamp.microservicemeetup.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    private ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid UserDTO dto) {

        User entity = modelMapper.map(dto, User.class);
        entity = userService.saveUser(entity);

        return modelMapper.map(entity, UserDTO.class);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO findUserById(@PathVariable Integer id) {

        return userService
                .findUserById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByUserById(@PathVariable Integer id) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userService.deleteUser(user);
    }

    @PutMapping("{id}")
    public UserDTO updateUserById(@PathVariable Integer id,
                                          @RequestBody @Valid UserDTO userDTO) {

        return userService.findUserById(id).map(user -> {
            user.setNameUser(userDTO.getNameUser());
            user.setDateRegistryUser(userDTO.getDateRegistryUser());
            user = userService.updateUser(user);
            return modelMapper.map(user, UserDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); //
    }

    @GetMapping
    public Page<UserDTO> findAllUsers(UserDTO dto, Pageable pageRequest) {
        User filter = modelMapper.map(dto, User.class);
        Page<User> result = userService.findAllUsers(filter, pageRequest);

        List<UserDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, UserDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<UserDTO>(list, pageRequest, result.getTotalElements());
    }
}