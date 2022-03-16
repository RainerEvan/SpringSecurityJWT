package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.repo.RoleRepository;
import com.example.demo.repo.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService{
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public void addRoleToUser(String email, String roleName){
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);

        user.getRoles().add(role);
    }

    public User getUser(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
