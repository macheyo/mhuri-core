package zw.co.macheyo.mhuricore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import zw.co.macheyo.mhuricore.model.AuthProvider;
import zw.co.macheyo.mhuricore.model.User;
import zw.co.macheyo.mhuricore.repository.UserRepository;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        if(!userRepository.existsByEmail("admin@macheyo.co.zw")) {
            admin.setName("admin");
            admin.setEmail("admin@macheyo.co.zw");
            admin.setPassword(passwordEncoder.encode("pass"));
            admin.setProvider(AuthProvider.local);
            admin.setCreatedBy("system");
            userRepository.save(admin);
        }
    }
}
