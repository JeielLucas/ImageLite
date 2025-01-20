package com.jeiellucas.imageliteapi.application.users;

import com.jeiellucas.imageliteapi.domain.entity.User;
import com.jeiellucas.imageliteapi.domain.exception.DuplicatedTupleException;
import com.jeiellucas.imageliteapi.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity save(@RequestBody UserDTO dto){
        try{
            User user = userMapper.mapToUser(dto);

            userService.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (DuplicatedTupleException ex){
            Map<String, String> jsonResult = Map.of("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity authenticate(@RequestBody CredentialsDTO dto){
        var token = userService.authenticate(dto.getEmail(), dto.getPassword());

        if(token == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(token);
    }
}
