package com.innovationm.hotel.controllers;

import com.innovationm.hotel.model.primary.User;
import com.innovationm.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user)
    {
        User user1=userService.addUser(user);
        return ResponseEntity.ok(user1);
    }
    @GetMapping("/getUserList")
    public ResponseEntity<List<User>> getUserList()
    {
        List<User> userList=userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
    @GetMapping("/getUserById")
    public ResponseEntity<User> getUser(@RequestParam("userId") long userId)
    {
        User user=userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/login")
    public ResponseEntity<User> login(@RequestHeader String X_AUTH_USER, HttpServletRequest request)
    {
        User user=userService.getUserByUserName(X_AUTH_USER);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/updateProfile")
    public ResponseEntity<User> updateUser(@RequestParam("id") long id,@Valid @RequestBody User user)
    {
        User user1=userService.updateUser(user,id);
        return ResponseEntity.ok(user1);
    }
    @DeleteMapping("/deletProfile")
    public ResponseEntity<String> delteUser(@RequestParam("id") long id)
    {
        userService.deleteUser(id);
        return ResponseEntity.ok("Your Profile is Deleted SuccesFully!");
    }


}
