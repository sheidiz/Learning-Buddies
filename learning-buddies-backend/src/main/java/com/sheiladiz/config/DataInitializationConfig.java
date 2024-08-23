package com.sheiladiz.config;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializationConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final SkillCategoryRepository skillCategoryRepository;
    private final SkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializationConfig(UserRepository userRepository, ProfileRepository profileRepository, SkillCategoryRepository skillCategoryRepository, SkillRepository skillRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.skillCategoryRepository = skillCategoryRepository;
        this.skillRepository = skillRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(String... args) throws Exception {
        if(skillRepository.count() == 0){
            List<SkillCategory> categories = List.of(
                    new SkillCategory("Programación"),
                    new SkillCategory("Frontend"),
                    new SkillCategory("Backend"),
                    new SkillCategory("Desarrollo Web"),
                    new SkillCategory("Desarrollo Móvil"),
                    new SkillCategory("Desarrollo de Juegos"),
                    new SkillCategory("Diseño UX/UI"),
                    new SkillCategory("Data Science"),
                    new SkillCategory("DevOps"),
                    new SkillCategory("Seguridad Informática")
            );
            skillCategoryRepository.saveAll(categories);

            List<Skill> skills = List.of(
                    new Skill("HTML", List.of(categories.get(3),categories.get(1))),
                    new Skill("CSS", List.of(categories.get(3),categories.get(1))),
                    new Skill("JavaScript", List.of(categories.get(3),categories.get(1))),
                    new Skill("React", List.of(categories.get(3),categories.get(1),categories.get(0))),
                    new Skill("Node.js", List.of(categories.get(2),categories.get(3),categories.get(0))),
                    new Skill("Python", List.of(categories.get(2),categories.get(7),categories.get(0))),
                    new Skill("Django", List.of(categories.get(2),categories.get(3),categories.get(7))),
                    new Skill("Java", List.of(categories.get(2),categories.get(3),categories.get(4))),
                    new Skill("Spring Boot", List.of(categories.get(2),categories.get(3),categories.get(0))),
                    new Skill("SQL", List.of(categories.get(8),categories.get(2),categories.get(7))),
                    new Skill("Docker", List.of(categories.get(5),categories.get(0))),
                    new Skill("Kubernetes", List.of(categories.get(5),categories.get(0))),
                    new Skill("AWS (Amazon Web Services)", List.of(categories.get(5),categories.get(2))),
                    new Skill("Azure", List.of(categories.get(5),categories.get(2))),
                    new Skill("Git", List.of(categories.get(5),categories.get(0))),
                    new Skill("Machine Learning", List.of(categories.get(7),categories.get(0))),
                    new Skill("Tableau", List.of(categories.get(7),categories.get(9))),
                    new Skill("Unity", List.of(categories.get(6), categories.get(4))),
                    new Skill("C++", List.of(categories.get(0),categories.get(6)))
            );
            skillRepository.saveAll(skills);
        }
        if(userRepository.count() == 0){
            List<User> users = List.of(
                    new User("sheila1@gmail.com", passwordEncoder.encode("123456"),"local", true, true, true, true),
                    new User("sheila2@gmail.com", passwordEncoder.encode("123456"),"local", true, true, true, true)
            );
            userRepository.saveAll(users);

            List<Profile> profiles = List.of(
                    new Profile(users.get(0),"/src/assets/users/1.png", "#FF8A8A", "Sheila1", "Argentina", "Estudiante Front-end", "Soy Sheila 1", "/sheila1"),
                    new Profile(users.get(1),"/src/assets/users/2.png","#3795BD", "Sheila2", "Argentina", "Estudiante Back-end", "Soy Sheila 2", "/sheila2")
            );
            profileRepository.saveAll(profiles);
        }
    }
}
