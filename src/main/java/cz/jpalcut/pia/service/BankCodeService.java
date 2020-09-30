package cz.jpalcut.pia.service;

import cz.jpalcut.pia.config.BankConfig;
import cz.jpalcut.pia.dao.BankCodeDAO;
import cz.jpalcut.pia.model.BankCode;
import cz.jpalcut.pia.service.interfaces.IBankCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankCodeService implements IBankCodeService {

    private BankCodeDAO bankCodeDAO;

    private BankConfig bankConfig;

    @Autowired
    public BankCodeService(BankCodeDAO bankCodeDAO, BankConfig bankConfig) {
        this.bankCodeDAO = bankCodeDAO;
        this.bankConfig = bankConfig;
    }

    /**
     * Vrátí seznam kódů bank
     *
     * @return seznam kódů bank
     */
    @Override
    public List<BankCode> getBankCodes() {
        return bankCodeDAO.findAll();
    }

    /**
     * Vrátí stránku bankovních kódů k zobrazení podle omezení
     *
     * @param pageable omezení pro výběr
     * @return stránka obsahující bankovní kódy
     */
    @Override
    public Page<BankCode> getBankCodesPageable(Pageable pageable) {
        return bankCodeDAO.findAll(pageable);
    }

    /**
     * Aktualizace bankovních kódů
     *
     * @return true - proběhlo v pořádku, false - nastala chyba
     */
    @Override
    public boolean updateBankCodes() {
        BankCode bankCode = new BankCode();
        List<BankCode> bankCodes = bankCode.loadBankCodes(bankConfig.getBankCodesUrl());
        if (bankCodes == null) {
            return false;
        }
        return bankCodeDAO.saveAll(bankCodes) != null;
    }
}
