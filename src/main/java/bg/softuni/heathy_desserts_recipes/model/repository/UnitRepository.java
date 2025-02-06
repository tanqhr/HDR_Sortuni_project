package bg.softuni.heathy_desserts_recipes.model.repository;


import bg.softuni.heathy_desserts_recipes.model.entity.unit.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    Optional<UnitEntity> findUnitByName (String unitName);

    @Query("select distinct u.name from UnitEntity u")
    List<String> findAllDistinctNames ();
}
