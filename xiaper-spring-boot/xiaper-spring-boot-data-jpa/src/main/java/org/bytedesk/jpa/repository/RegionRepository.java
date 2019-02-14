package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByCode(String code);

    List<Region> findByParent(Region region);

    List<Region> findByType(String type);

    List<Region> findByTypeOrType(String provinceType, String cityType);

    Optional<Region> findFirstByNameContaining(String name);
}
