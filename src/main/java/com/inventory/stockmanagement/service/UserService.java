package com.inventory.stockmanagement.service;

import com.inventory.stockmanagement.model.user.UserAccount;

import java.util.*;

public interface UserService {
    List<UserAccount> findAll();
    List<UserAccount> search(String q);
    UserAccount findById(Long id);
    UserAccount save(UserAccount u);
    void delete(Long id);
    Optional<UserAccount> authenticate(String username, String password);
}