package zw.co.macheyo.mhuricore.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Getter
@Setter
@NoArgsConstructor
@Audited
public class AccountTransaction {
    @EmbeddedId
    AccountTransactionKey id;
    @JsonIgnore
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    Account account;
    @ManyToOne
    @MapsId("transactionId")
    @JoinColumn(name = "transaction_id")
    Transaction transaction;

    double amount;
    Currency currency;
    public AccountTransaction(Account account, Transaction transaction, double amount) {
        this.id = new AccountTransactionKey(account.getId(),transaction.getId());
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;

    }
}
