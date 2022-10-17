package com.Omer.services;

import com.Omer.dto.UserDto;
import com.Omer.entity.User;

import java.util.List;

public interface UserService {
     UserDto createUser(UserDto userDto);
     List<UserDto> getUsers();
     UserDto getUser(Long id);
     UserDto updateUser(Long id,UserDto userDto);
     Boolean deleteUser(Long id);
}
