package zw.co.macheyo.mhuricore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.macheyo.mhuricore.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
