package com.logicea.user;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> selectAllCustomers();

    Optional<User> selectUserById(Integer id);

    void insertUser(User user);

    boolean personWithEmailExists(String email);

    void deleteUserById(Integer id);

    boolean personWithIdExists(Integer id);

    void updateUser(User update);

    Optional<User> selectUserByEmail(String email);
}
