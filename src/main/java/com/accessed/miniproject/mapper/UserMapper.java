package com.accessed.miniproject.mapper;

import com.accessed.miniproject.dto.request.UserCreationRequest;
import com.accessed.miniproject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {

    @Mapping(target = "appointmentList", ignore = true)
    @Mapping(target = "reviewList", ignore = true)
    @Mapping(target = "favoriteList", ignore = true)
    User toUser (UserCreationRequest request);

}
