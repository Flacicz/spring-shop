package com.example.springshop.mapper;

import com.example.springshop.dto.ProductDTO;
import com.example.springshop.dto.UserDTO;
import com.example.springshop.entity.Product;
import com.example.springshop.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromDTOToUser(UserDTO userDTO);

    @InheritInverseConfiguration
    UserDTO fromUserToDTO(User user);

    List<User> fromUserDTOToUserList(List<UserDTO> userDTOs);

    List<UserDTO> fromUserToUserDTOList(List<User> users);
}
