package com.logicea.user;

import com.logicea.exception.DuplicateResourceException;
import com.logicea.exception.RequestValidationException;
import com.logicea.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public void addUser(UserRegistrationRequest userRegistrationRequest){
        //check if email exits
        System.out.println("boolean email exists count = " + userDAO.personWithEmailExists(userRegistrationRequest.email()));
        if(userDAO.personWithEmailExists(userRegistrationRequest.email())){
            throw new DuplicateResourceException("Email already taken");
        }
        //add
        User user = new User(
                userRegistrationRequest.email(),
                passwordEncoder.encode(userRegistrationRequest.password()),
                userRegistrationRequest.role());
        userDAO.insertUser(user);
    }

    public void deleteCustomerById(Integer id){
        //check if email exits
        if(!userDAO.personWithIdExists(id)){
           throw new ResourceNotFoundException("User with id [%s] not found " .formatted(id));
        }
        //add
        userDAO.deleteUserById(id);
    }

    public void updateCustomer(Integer customerId, UserUpdateRequest userUpdateRequest){

        User user = userDAO.selectUserById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user with id [%s] NOT found"
                                .formatted(customerId)));

        boolean changes = false;
        //System.out.println("customer name " + customer.getName() + "-" + customerUpdateRequest.name()
            //    .equals(customer.getName()));

        if(userUpdateRequest.role() != null && !userUpdateRequest.role()
                .equals(user.getRole()) ){
            user.setRole(userUpdateRequest.role());
            changes = true;
        }

        if(userUpdateRequest.email() != null && !userUpdateRequest.email()
                .equals(user.getEmail()) ){
            //check if email exits
            if(userDAO.personWithEmailExists(userUpdateRequest.email())){
                throw new DuplicateResourceException("Email already taken");
            }

            user.setEmail(userUpdateRequest.email());
            changes = true;
        }

       /*if(userUpdateRequest.password() != null && !userUpdateRequest.password()
                .equals(user.getPassword()) ){
            user.setPassword(userUpdateRequest.password());
            changes = true;
        }*/

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        userDAO.updateUser(user);

    }
}
