package bg.softuni.heathy_desserts_recipes.model.repository;


import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserById (Long id);

    UserEntity getUserEntityById (Long id);

    Optional<UserEntity> findUserByEmail (String email);

    void deleteById(Long id);


    boolean existsByEmail (String email);
}
