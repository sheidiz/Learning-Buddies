package com.sheiladiz.config;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Configuration
public class DataInitializationConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final SkillCategoryRepository skillCategoryRepository;
    private final SkillRepository skillRepository;
    private final PasswordEncoder passwordEncoder;

    public void run(String... args) throws Exception {
        if (skillRepository.count() == 0) {
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
                    new Skill("HTML", Set.of(categories.get(3), categories.get(1))),
                    new Skill("CSS", Set.of(categories.get(3), categories.get(1))),
                    new Skill("JavaScript", Set.of(categories.get(3), categories.get(1))),
                    new Skill("React", Set.of(categories.get(3), categories.get(1), categories.get(0))),
                    new Skill("Node.js", Set.of(categories.get(2), categories.get(3), categories.get(0))),
                    new Skill("Python", Set.of(categories.get(2), categories.get(7), categories.get(0))),
                    new Skill("Django", Set.of(categories.get(2), categories.get(3), categories.get(7))),
                    new Skill("Java", Set.of(categories.get(2), categories.get(3), categories.get(4))),
                    new Skill("Spring Boot", Set.of(categories.get(2), categories.get(3), categories.get(0))),
                    new Skill("SQL", Set.of(categories.get(8), categories.get(2), categories.get(7))),
                    new Skill("Docker", Set.of(categories.get(5), categories.get(0))),
                    new Skill("Kubernetes", Set.of(categories.get(5), categories.get(0))),
                    new Skill("AWS (Amazon Web Services)", Set.of(categories.get(5), categories.get(2))),
                    new Skill("Azure", Set.of(categories.get(5), categories.get(2))),
                    new Skill("Git", Set.of(categories.get(5), categories.get(0))),
                    new Skill("Machine Learning", Set.of(categories.get(7), categories.get(0))),
                    new Skill("Tableau", Set.of(categories.get(7), categories.get(9))),
                    new Skill("Unity", Set.of(categories.get(6), categories.get(4))),
                    new Skill("C++", Set.of(categories.get(0), categories.get(6)))
            );
            skillRepository.saveAll(skills);
        }
        if (userRepository.count() == 0) {
            List<User> users = List.of(
                    new User("juanamendoza@gmail.com", passwordEncoder.encode("123456"), "local", true, true, true, true),
                    new User("juansuarez@gmail.com", passwordEncoder.encode("123456"), "local", true, true, true, true),
                    new User("maria.barrios@gmail.com", passwordEncoder.encode("123456"), "local", true, true, true, true)
            );
            userRepository.saveAll(users);

            List<Profile> profiles = List.of(
                    new Profile(users.get(0), "Juana Mendoza", "/src/assets/users/1.png", "#FF8A8A", "Mujer", "Argentina", "Estudiante Front-end", "Soy una estudiante de Frontend.", "/juana_m"),
                    new Profile(users.get(1), "Juan Suarez", "/src/assets/users/6.png", "#3795BD", "Hombre", "Argentina", "Estudiante Back-end", "Soy un estudiante de programación. Estoy estudiando en Edu123.", "/juan123")
            );
            profileRepository.saveAll(profiles);
        }
    }
}
