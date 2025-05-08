package com.jeroka.auth.service;

import com.jeroka.auth.model.User;
import com.jeroka.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User non trouvé avec l'ID : " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User non trouvé avec l'email : " + email));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Un user existe déjà avec cet email");
        }
        user.setDateCreation(LocalDateTime.now());
        user.setDateMiseAJour(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        if (!user.getEmail().equals(userDetails.getEmail()) && 
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalArgumentException("Un user existe déjà avec cet email");
        }

        user.setEmail(userDetails.getEmail());
        user.setNom(userDetails.getNom());
        user.setPrenom(userDetails.getPrenom());
        user.setTelephone(userDetails.getTelephone());
        user.setAdresse(userDetails.getAdresse());
        user.setCodePostal(userDetails.getCodePostal());
        user.setVille(userDetails.getVille());
        user.setPays(userDetails.getPays());
        user.setDateMiseAJour(LocalDateTime.now());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}