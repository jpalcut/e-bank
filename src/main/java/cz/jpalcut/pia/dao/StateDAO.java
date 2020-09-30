package cz.jpalcut.pia.dao;

import cz.jpalcut.pia.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Rozhraní pro přístup k státům
 */
@Repository
public interface StateDAO extends JpaRepository<State, Integer> {

}
