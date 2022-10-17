package com.Omer.services.servicesImpl;

import com.Omer.entity.User;
import com.Omer.repository.UserRepository;
import com.Omer.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserService {

    private final UserRepository userRepository;
    public UserServicesImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public User createUser(User user) {
        user.setCreateData(new Date());
        user.setCreatedBy("Admin");
        return userRepository.save(user);
    }
    @Override
    public List<User> getUsers() {
        //
        return userRepository.findAll();
    }
    @Override
    public User getUser(Long id) {
        Optional <User> user = userRepository.findById(id);
        if(user.isPresent()) // geriye user döndümü
        {
            return user.get();
        }
        return null;
    }
    @Override
    public User updateUser(Long id,User user) {
        Optional <User> finduser = userRepository.findById(id);
        if(finduser.isPresent()) // geriye user döndümü
        {
            finduser.get().setFirstname(user.getFirstname());
            finduser.get().setLastname(user.getLastname());
            finduser.get().setUpdateAt(new Date());
            finduser.get().setUpdateBy("Admin");

            return userRepository.save(finduser.get());
        }
        return null;
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional <User> finduser = userRepository.findById(id);
        if(finduser.isPresent()) // geriye user döndümü
        {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
