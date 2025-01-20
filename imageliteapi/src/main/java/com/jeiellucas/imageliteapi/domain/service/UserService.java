package com.jeiellucas.imageliteapi.domain.service;

import com.jeiellucas.imageliteapi.domain.AccessToken;
import com.jeiellucas.imageliteapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    User save(User user);
    AccessToken authenticate(String email, String password);
}
