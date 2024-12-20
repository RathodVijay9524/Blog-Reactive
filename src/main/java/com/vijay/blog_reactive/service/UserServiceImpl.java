package com.vijay.blog_reactive.service;

import com.vijay.blog_reactive.entity.Role;
import com.vijay.blog_reactive.entity.User;
import com.vijay.blog_reactive.exception.ResourceNotFoundException;
import com.vijay.blog_reactive.mapper.Mapper;
import com.vijay.blog_reactive.repository.RoleRepository;
import com.vijay.blog_reactive.repository.UserRepository;
import com.vijay.blog_reactive.request.UserRequest;
import com.vijay.blog_reactive.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    @Override
    public CompletableFuture<UserResponse> createUser(UserRequest userRequest) {
        return CompletableFuture.supplyAsync(()->{

                log.info("Creating user: {}", userRequest.getUsername());
                Set<Role> roles = userRequest.getRoleIds().stream()
                        .map(roleId -> roleRepository.findById(roleId)
                                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId)))
                        .collect(Collectors.toSet());

                User user = User.builder()
                        .name(userRequest.getName())
                        .username(userRequest.getUsername())
                        .email(userRequest.getEmail())
                        .password(userRequest.getPassword())
                        .roles(roles)
                        .build();
            System.out.println("roles :"+roles);
                User savedUser = repository.save(user);
                return Mapper.mapToUserResponse(savedUser);
        });
    }

    @Override
    public CompletableFuture<UserResponse> getUserById(Long id) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching user with ID: {}", id);
            User user = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
            return Mapper.mapToUserResponse(user);
        });
    }

    @Override
    public CompletableFuture<List<UserResponse>> getAllUsers() {
        return CompletableFuture.supplyAsync(()->{
            log.info("Fetching all users");
            List<User> users = repository.findAll();
            return users.stream()
                    .map(user -> Mapper.toDto(user,UserResponse.class))
                    .collect(Collectors.toList());
        });
    }

    @Override
    public CompletableFuture<UserResponse> updateUser(UserRequest userRequest, Long id) {
        return CompletableFuture.supplyAsync(()->{
            log.info("Updating user with ID: {}", id);
            User user = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
            user.setName(userRequest.getName());
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());

            repository.save(user);
            return Mapper.toDto(user,UserResponse.class);
        });
    }

    @Override
    public CompletableFuture<Void> deleteUser(Long id) {
        return CompletableFuture.runAsync(()->{
            log.info("Deleting user with ID: {}", id);
            User user = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
            repository.delete(user);
        });
    }
}
