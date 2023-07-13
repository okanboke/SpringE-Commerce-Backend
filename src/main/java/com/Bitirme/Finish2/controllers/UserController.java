package com.Bitirme.Finish2.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.Bitirme.Finish2.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.Bitirme.Finish2.entities.User;
import com.Bitirme.Finish2.exceptions.UserNotFoundException;
import com.Bitirme.Finish2.responses.UserResponse;
import com.Bitirme.Finish2.services.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;


    public UserController(UserService userService){   //constructor injection
        this.userService = userService;

    }
    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }   //userService sınıfındaki metoda gider ve repository'e bağlanır.

    @PostMapping
    public User createUser(@RequestBody User newUser) { //user create etmek için
        return  userService.saveOneUser(newUser);
    } //servisteki metoduna gider ve repositorye bağlanıp yeni kullanıcı kaydeder.

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) { //tek bir user çekilmek istendiğinde Service sınıfına gider ve metoduyla gerçekleştirir.
        User user = userService.getOneUserById(userId);

        if(user == null) { //öyle bir user yoksa
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }
    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser) { //var olan bir useri güncellemek için
        return userService.updateOneUser(userId, newUser); //Service de metoduna gidip user güncelleme ve ekleme işlemlerini yapar.
    }
    @DeleteMapping("/{userId}") //Service'de metoduna gidecek ve id sini aldığı kişiyi silecek
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}") //aktivite endpoint userın aktivitelerini gösterecek
    public List<Object> getUserActivity(@PathVariable Long userId) {
        return userService.getUserActivity(userId); //serviste metoduna gidecek ve userın aktivitelerini listede gösterecek
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound() { //user bulunamaz ise buraya gelecek

    }

}
