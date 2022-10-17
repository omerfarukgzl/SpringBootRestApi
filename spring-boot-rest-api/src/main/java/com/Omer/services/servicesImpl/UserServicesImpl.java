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

}
