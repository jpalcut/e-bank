package cz.jpalcut.pia.service.interfaces;

import cz.jpalcut.pia.model.BankCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBankCodeService {

    /**
     * Vrátí seznam kódů bank
     *
     * @return seznam kódů bank
     */
    List<BankCode> getBankCodes();

    /**
     * Vrátí stránku bankovních kódů k zobrazení podle omezení
     *
     * @param pageable omezení pro výběr
     * @return stránka obsahující bankovní kódy
     */
    Page<BankCode> getBankCodesPageable(Pageable pageable);

    /**
     * Aktualizuje bankovních kódy
     *
     * @return true - proběhlo v pořádku, false - nastala chyba
     */
    boolean updateBankCodes();

}
