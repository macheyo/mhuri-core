package zw.co.macheyo.mhuricore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.macheyo.mhuricore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
