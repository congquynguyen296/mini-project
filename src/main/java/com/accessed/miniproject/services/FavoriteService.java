package com.accessed.miniproject.services;

import com.accessed.miniproject.enums.ERateType;
import com.accessed.miniproject.model.Favorite;
import com.accessed.miniproject.model.User;
import com.accessed.miniproject.repositories.FavoriteRepository;
import com.accessed.miniproject.repositories.LocationRepository;
import com.accessed.miniproject.repositories.StaffRepository;
import com.accessed.miniproject.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteService {
    LocationRepository locationRepository;
    StaffRepository staffRepository;
    UserRepository userRepository;
    FavoriteRepository favoriteRepository;

    public void createFavorite(String user, String id) {
        String subjectId = "";
        ERateType type = null;
        if(locationRepository.existsById(id)) {
            subjectId = locationRepository.getLocationById(id).getId();
            type = ERateType.BUSINESS;
        }
        else if(staffRepository.existsById(id)) {
            subjectId = staffRepository.getStaffById(id).getId();
            type = ERateType.STAFF;
        }

        User userObject = userRepository.getUserByUsername(user);
        Favorite favorite = Favorite.builder().subjectId(subjectId).subjectType(type).user(userObject).build();
        favoriteRepository.save(favorite);
    }

    public void deleteFavorite(String id) {
        favoriteRepository.deleteById(id);
    }
}
