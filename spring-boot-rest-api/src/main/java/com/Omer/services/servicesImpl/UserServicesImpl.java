package com.Omer.services.servicesImpl;

import com.Omer.dto.UserDto;
import com.Omer.entity.User;
import com.Omer.repository.UserRepository;
import com.Omer.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    public UserServicesImpl(ModelMapper modelMapper, UserRepository userRepository){
        this.modelMapper = modelMapper;
        this.userRepository=userRepository;
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        user.setCreateData(new Date());
        user.setCreatedBy("Admin");
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }
    @Override
    public List<UserDto> getUsers() {

        List<User> users = userRepository.findAll();
        List<UserDto> userDto= users.stream().map(user -> modelMapper.map(users,UserDto.class)).collect(Collectors.toList());
        return userDto ;
    }
    @Override
    public UserDto getUser(Long id) {
        Optional <User> user = userRepository.findById(id);
        if(user.isPresent()) // geriye user döndümü
        {
            return modelMapper.map(user.get(),UserDto.class);
        }
        return null;
    }
    @Override
    public UserDto updateUser(Long id,UserDto userDto) {
        Optional <User> finduser = userRepository.findById(id);
        if(finduser.isPresent()) // geriye user döndümü
        {
            finduser.get().setFirstname(userDto.getFirstname());
            finduser.get().setLastname(userDto.getLastname());
            finduser.get().setUpdateAt(new Date());
            finduser.get().setUpdateBy("Admin");

            return modelMapper.map(userRepository.save(finduser.get()),UserDto.class);
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
