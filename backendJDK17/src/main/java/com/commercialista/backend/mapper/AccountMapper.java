package com.commercialista.backend.mapper;

import org.springframework.beans.BeanUtils;

import com.commercialista.backend.dto.AccountDTO;
import com.commercialista.backend.models.Account;

public class AccountMapper {
	
    public static AccountDTO toDto(Account entity) {
        if (entity == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
