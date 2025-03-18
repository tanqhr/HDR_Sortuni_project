package bg.softuni.heathy_desserts_recipes.model.repository;

import bg.softuni.heathy_desserts_recipes.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
