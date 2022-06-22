package zw.co.macheyo.mhuricore.service.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.macheyo.mhuricore.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
