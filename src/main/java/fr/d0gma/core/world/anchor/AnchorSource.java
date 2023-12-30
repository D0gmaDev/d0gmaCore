package fr.d0gma.core.world.anchor;

import java.util.List;
import java.util.Optional;

public interface AnchorSource {

    Optional<Anchor> findOne(String key);

    List<Anchor> findMany(String key);

    List<Anchor> findAll();

}
