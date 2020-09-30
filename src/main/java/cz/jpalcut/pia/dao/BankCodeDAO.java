package cz.jpalcut.pia.dao;

import cz.jpalcut.pia.model.BankCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCodeDAO extends JpaRepository<BankCode, Integer> {

    /**
     * Vrátí stránku bankovních kódů podle omezení
     *
     * @param pageable omezení pro bankovních kódů
     * @return stránka bankovních kódů
     */
    Page<BankCode> findAll(Pageable pageable);

}
