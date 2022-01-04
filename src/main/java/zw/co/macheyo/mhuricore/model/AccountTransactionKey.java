package zw.co.macheyo.mhuricore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountTransactionKey implements Serializable {
    @Column(name = "account_id")
    Long accountId;
    @Column(name = "transaction_id")
    Long transactionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountTransactionKey)) return false;
        AccountTransactionKey that = (AccountTransactionKey) o;
        return Objects.equals(getAccountId(), that.getAccountId()) && Objects.equals(getTransactionId(), that.getTransactionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getTransactionId());
    }
}
