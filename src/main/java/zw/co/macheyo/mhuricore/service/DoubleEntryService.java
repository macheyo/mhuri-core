package zw.co.macheyo.mhuricore.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public interface DoubleEntryService {
    void record(String accountToDebit, String accountToCredit, String reference, Double amount);
}
