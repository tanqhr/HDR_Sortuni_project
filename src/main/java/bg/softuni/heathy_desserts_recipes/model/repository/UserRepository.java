package bg.softuni.heathy_desserts_recipes.model.repository;


import bg.softuni.heathy_desserts_recipes.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserById (Long id);

    UserEntity getUserEntityById (Long id);
    Optional<UserEntity> findByEmail (String email);
    Optional<UserEntity> findUserByEmail (String email);
    Optional<UserEntity> findByUsername(String username);
    void deleteById(Long id);
    void deleteAllByActiveIsFalse();
    Optional<UserEntity> findUserEntityByUsername(String name);

    boolean existsByEmail (String email);
}
