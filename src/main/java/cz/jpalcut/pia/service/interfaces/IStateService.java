package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.State;

import java.util.List;

/**
 * Rozhraní pro službu spravující státy
 */
public interface IStateService {

    /**
     * Vrátí seznam všech států
     *
     * @return seznam států
     */
    List<State> getAllStates();

}
