package pt.solutions.af.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.solutions.af.chat.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
}
