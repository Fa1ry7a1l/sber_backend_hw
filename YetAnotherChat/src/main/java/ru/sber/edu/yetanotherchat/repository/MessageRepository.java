package ru.sber.edu.yetanotherchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.yetanotherchat.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
