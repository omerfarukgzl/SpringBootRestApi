package com.Omer.api;

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
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User resultUser=userService.createUser(user);
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity <List<User>> getUser()
    {
        List<User> resultUser=userService.getUsers();
        return ResponseEntity.ok(resultUser);
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity <User> getUser(@PathVariable ("id") Long id)
    {
        User resultUser=userService.getUser(id);
        return ResponseEntity.ok(resultUser);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity <User> updateUser(@PathVariable ("id") Long id,@RequestBody User user)
    {
        User resultUser=userService.updateUser(id,user);
        return ResponseEntity.ok(resultUser);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity <Boolean> deleteUser(@PathVariable ("id") Long id)
    {
        Boolean resultUser=userService.deleteUser(id);
        return ResponseEntity.ok(resultUser);
    }
}