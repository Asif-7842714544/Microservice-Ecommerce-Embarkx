package com.ecommerce.user.reporsitory;

import com.ecommerce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
