package com.zosh.treading.repository;

import com.zosh.treading.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
