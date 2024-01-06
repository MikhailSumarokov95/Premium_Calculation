package ru.sumarokov.premium_calculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.helper.Role;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT COUNT(*) FROM User u WHERE u.role LIKE :role")
    Long getSumUsersSelectedRole(@Param("role") Role role);
}
