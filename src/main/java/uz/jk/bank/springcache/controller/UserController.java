package uz.jk.bank.springcache.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.jk.bank.springcache.entity.UserEntity;
import uz.jk.bank.springcache.service.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public UserEntity create(UserEntity user) {
        return userService.saveUser(user);
    }

    @GetMapping("/get/{id}")
    public UserEntity getUserById(Long id) {
        return userService.findById(id);
    }

    @GetMapping("/{username}")
    public UserEntity getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping("/update")
    public UserEntity updateUser(UserEntity user) {
        return userService.updateUser(user);
    }

    @PostMapping("/delete")
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
