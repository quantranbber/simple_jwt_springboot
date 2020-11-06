package application.controllers;

import application.entities.Info;
import application.entities.User;
import application.models.InfoDTO;
import application.models.UserDTO;
import application.repositories.InfoRepository;
import application.services.UserService;
import application.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class CommonApi {
    private static Logger logger = LoggerFactory.getLogger(CommonApi.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private InfoRepository infoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/generateToken")
    public ResponseEntity<?> generateToken(@RequestBody UserDTO userDTO) {
        try {
            User user = userService.findByUsername(userDTO.getUsername());
            String token = null;
            if (passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
                token = "Bearer " + jwtUtils.generateToken(userDTO.getUsername());
            }
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest()
                                 .body("failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setCreatedDate(new Date());
            userService.register(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest()
                                 .body("failed");
        }
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<?> getInfo(@PathVariable String name) {
        try {
            Info info = infoRepository.getInfoByName(name);
            return ResponseEntity.ok(info.getValue());
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest()
                    .body("failed");
        }
    }

    @PostMapping("/info/create")
    public ResponseEntity<?> saveInfo(@RequestBody InfoDTO dto) {
        try {
            Info info = new Info();
            info.setName(dto.getName());
            info.setValue(dto.getValue());
            infoRepository.saveInfo(info);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest()
                    .body("failed");
        }
    }

    @PostMapping("/info/update")
    public ResponseEntity<?> updateInfo(@RequestBody Info info) {
        try {
            Info result = infoRepository.getInfoById(info.getId());
            result.setId(info.getId());
            result.setName(info.getName());
            result.setValue(info.getValue());
            infoRepository.updateInfo(result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.badRequest()
                    .body("failed");
        }
    }

    @PostMapping("/test")
    public ResponseEntity<?> login() {
        try {
            return ResponseEntity.ok("hello");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest()
                    .body("failed");
        }
    }
}
