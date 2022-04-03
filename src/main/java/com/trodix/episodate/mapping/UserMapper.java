package com.trodix.episodate.mapping;

import com.trodix.episodate.core.entity.User;
import com.trodix.episodate.model.request.UserPublicRequest;
import com.trodix.episodate.model.response.UserPublicResponse;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // out
    UserPublicResponse entityToModel(User user);

    Iterable<UserPublicResponse> entityListToModel(Iterable<User> users);

    // in
    User modelToEntity(UserPublicRequest userRequest);

}
