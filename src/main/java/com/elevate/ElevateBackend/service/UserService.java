package com.elevate.ElevateBackend.service;

import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.ChangePasswordRequest;
import com.elevate.ElevateBackend.dto.LoginRequest;
import com.elevate.ElevateBackend.dto.SignupRequest;
import com.elevate.ElevateBackend.dto.UpdateProfileRequest;
import com.elevate.ElevateBackend.dto.UserProfileResponse;
import com.elevate.ElevateBackend.entity.Role;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dateOfBirth(request.getDateOfBirth())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }
    
    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        return user;
    }
    
    public UserProfileResponse getProfile(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(

                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getDateOfBirth(),
                user.getProfileImage()

        );
    }
    
    public String updateProfile(String email,
            UpdateProfileRequest request){

User user = userRepository.findByEmail(email)
.orElseThrow(() -> new RuntimeException("User not found"));

user.setFullName(request.getFullName());
user.setUsername(request.getUsername());
user.setDateOfBirth(request.getDateOfBirth());

userRepository.save(user);

return "Profile Updated Successfully";
}
    
    public String changePassword(String email,
            ChangePasswordRequest request){

User user = userRepository.findByEmail(email)
.orElseThrow(() -> new RuntimeException("User not found"));

if(!passwordEncoder.matches(
request.getOldPassword(),
user.getPassword())){

throw new RuntimeException("Old password is incorrect");
}

user.setPassword(
passwordEncoder.encode(
   request.getNewPassword()));

userRepository.save(user);

return "Password Changed Successfully";
}
    
    public String uploadProfilePicture(String email,
            MultipartFile file)
throws Exception {

User user = userRepository.findByEmail(email)
.orElseThrow(() ->
new RuntimeException("User not found"));

String fileName =
UUID.randomUUID() + "_" + file.getOriginalFilename();

Path path = Paths.get(uploadDir);

if (!Files.exists(path)) {

Files.createDirectories(path);

}

Files.copy(file.getInputStream(),
path.resolve(fileName),
StandardCopyOption.REPLACE_EXISTING);

user.setProfileImage(fileName);

userRepository.save(user);

return "Profile picture uploaded successfully";
}
    
    
}