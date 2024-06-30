package br.com.finsavior.events.processor.repository;

import br.com.finsavior.events.processor.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    @NotNull
    User getById(@NotNull Long id);
}