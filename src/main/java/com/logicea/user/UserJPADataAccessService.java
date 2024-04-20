package com.logicea.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJPADataAccessService implements UserDAO{

    private final UserRepository userRepository;

    public UserJPADataAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> selectAllCustomers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void insertUser(User customer) {
        userRepository.save(customer);
    }

    @Override
    public boolean personWithEmailExists(String email) {
        return userRepository.existsCustomerByEmail(email);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean personWithIdExists(Integer id) {
        return userRepository.existsCustomerById(id);
    }

    @Override
    public void updateUser(User customer) {
        userRepository.save(customer);
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return userRepository.findCustomerByEmail(email);
    }
}
