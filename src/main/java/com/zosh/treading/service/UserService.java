package com.zosh.treading.service;

import com.zosh.treading.domain.VerificationType;
import com.zosh.treading.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
    User findUserById(Long id);

    User enableTwoFactorAuthentication(VerificationType verificationType,String sendTo, User user);
    User updatePassword(User user, String newPassword);
}
