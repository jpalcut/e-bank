package cz.jpalcut.pia.service;

import cz.jpalcut.pia.dao.StateDAO;
import cz.jpalcut.pia.model.State;
import cz.jpalcut.pia.service.interfaces.IStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Služba pro správu států
 */
@Service
@Transactional
public class StateService implements IStateService {

    private StateDAO stateDAO;

    /**
     * Konstruktor třídy
     *
     * @param stateDAO StateDAO
     */
    @Autowired
    public StateService(StateDAO stateDAO) {
        this.stateDAO = stateDAO;
    }

    /**
     * Vrátí seznam všech států
     *
     * @return seznam států
     */
    @Override
    public List<State> getAllStates() {
        return stateDAO.findAll();
    }

}
