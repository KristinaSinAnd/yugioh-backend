package com.javariga6.yugioh.mapper;

import com.javariga6.yugioh.model.User;
import com.javariga6.yugioh.model.UserDTO;

public class UserMapper {
    public static UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
