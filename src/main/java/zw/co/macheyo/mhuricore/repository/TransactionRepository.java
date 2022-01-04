package zw.co.macheyo.mhuricore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zw.co.macheyo.mhuricore.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
