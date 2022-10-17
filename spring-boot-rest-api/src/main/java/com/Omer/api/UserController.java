package com.Omer.api;

import com.Omer.dto.UserDto;
import com.Omer.entity.User;
import com.Omer.repository.UserRepository;
import com.Omer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@ResponseBody
//@RequiredArgsConstructor
public class UserController {

    /*
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    */
    @Autowired
    private UserService userService;


    @PostMapping(value = "/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user)
    {
        UserDto resultUser=userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity <List<UserDto>> getUser()
    {
        List<UserDto> resultUser=userService.getUsers();
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity <UserDto> getUser(@PathVariable ("id") Long id)
    {
        UserDto resultUser=userService.getUser(id);
        return ResponseEntity.ok(resultUser);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity <UserDto> updateUser(@PathVariable ("id") Long id,@RequestBody UserDto userDto)
    {
        UserDto resultUser=userService.updateUser(id,userDto);
        return ResponseEntity.ok(resultUser);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity <Boolean> deleteUser(@PathVariable ("id") Long id)
    {
        Boolean resultUser=userService.deleteUser(id);
        return ResponseEntity.ok(resultUser);
    }
}