package ru.effective.mobile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.effective.mobile.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
