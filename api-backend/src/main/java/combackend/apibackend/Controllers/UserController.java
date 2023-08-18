package combackend.apibackend.Controllers;


import combackend.apibackend.Models.User;
import combackend.apibackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers(){

        return userRepository.findAll();

    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user){

        User userSaved = userRepository.save(user);

        if (userSaved != null && userSaved.getId() != null){

            return ResponseEntity.ok("User saved successfully");

        }
        else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @PutMapping
    public ResponseEntity<String> editUser(@PathVariable Long Id, @RequestBody User userToUpdate){
        Optional<User> exist = userRepository.findById(Id);

        if (exist.isPresent()){
            User userUpdate = exist.get();

            if (userToUpdate.getUsername() != null){
                userUpdate.setUsername(userToUpdate.getUsername());
            }
            if (userToUpdate.getAge() >= 0){
                userUpdate.setAge(userToUpdate.getAge());
            }
            if (userToUpdate.getName() != null) {
                userUpdate.setName(userToUpdate.getName());
            }
            if (userToUpdate.getPassword() != null){
                userUpdate.setPassword(userToUpdate.getPassword());
            }
            if (userToUpdate.getDescription() != null){
                userUpdate.setDescription(userToUpdate.getDescription());
            }
            else{
                userUpdate.setDescription("No description for this user");
            }
            userRepository.save(userUpdate);
            return ResponseEntity.ok("User updated successfully");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@PathVariable Long Id){
        Optional<User> existsForDelete = userRepository.findById(Id);

        if (existsForDelete.isPresent()){
            User userForDelete = existsForDelete.get();
            userRepository.delete(userForDelete);

            return ResponseEntity.ok("User deleted successfully!");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
