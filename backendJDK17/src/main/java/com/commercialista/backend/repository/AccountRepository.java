package com.commercialista.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.commercialista.backend.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query(value = "SELECT id_user FROM Accounts WHERE status = 'NON ATTIVO' AND DATE_PART('day', CURRENT_TIMESTAMP - data_pubb) > 6", nativeQuery = true)
	List<Long> findAccountsNotConfirmedAfter6Days();
	
	@Query(value = "DELETE FROM Accounts WHERE status = 'NON ATTIVO' AND DATE_PART('day', CURRENT_TIMESTAMP - data_pubb) > 6", nativeQuery = true)
	@Modifying
	int removeAccountsNonConfermatiDa6gg();

}

