package ru.sber.edu.yetanotherchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.edu.yetanotherchat.entity.Chat;
import ru.sber.edu.yetanotherchat.entity.User;

import java.util.List;
import java.util.Set;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Boolean existsByMembersAndIsMultiPersonFalse(Set<User> members);

    List<Chat> getAllByMembersContains(User member);
}
