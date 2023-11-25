package ru.sumarokov.premium_calculation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sumarokov.premium_calculation.entity.User;
import ru.sumarokov.premium_calculation.helper.Role;

import java.util.Collection;
import java.util.Collections;

public class UserDto implements UserDetails {

    private Long id;
    @NotEmpty(message = "Поле \"Имя\" должно быть заполнено")
    private String username;
    @NotEmpty(message = "Поле \"Пароль\" должно быть заполнено")
    private String password;
    @NotEmpty(message = "Поле \"Электронный адрес\" должно быть заполнено")
    @Email(message = "Неверный формат электронной почты")
    private String email;
    private Role role;

    public UserDto() {
    }

    public UserDto(Long id,
                   String username,
                   String email,
                   Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static UserDto toDto(User entity) {
        return new UserDto(entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole());
    }

    public User toEntity() {
        return new User(id, username, password, email, role);
    }
}
