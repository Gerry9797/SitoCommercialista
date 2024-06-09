package com.commercialista.backend.mapper;

import org.springframework.beans.BeanUtils;

import com.commercialista.backend.dto.UserDTO;
import com.commercialista.backend.models.User;

public class UserMapper {
	
    public static UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
