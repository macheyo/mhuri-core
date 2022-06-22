package zw.co.macheyo.mhuricore.service.doubleentry;

import org.springframework.stereotype.Component;

@Component
public interface DoubleEntryService {
    void addEntries(String accountToDebit, String accountToCredit, String reference, Double amount);
}
