package com.example.cleanapp.adapters.outbound.persistence.mapper;

import com.example.cleanapp.adapters.outbound.persistence.entity.UserEntity;
import com.example.cleanapp.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);
}