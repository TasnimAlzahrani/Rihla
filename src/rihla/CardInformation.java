package rihla;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Tasneem
 */
@Entity
@Table(name = "cardinformation")
public class CardInformation implements Serializable {

    @Id
    @Column(name = "cardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardId;

    @Column(name = "driverId")
    private int driverId;

    @Column(name = "accountNumber")
    private int accountNumber;

    @Column(name = "IBAN")
    private String IBAN;

    @Column(name = "bank")
    private String bank;

    public CardInformation() {
    }

    public CardInformation(int accountNumber, String IBAN, String bank) {
        this.accountNumber = accountNumber;
        this.IBAN = IBAN;
        this.bank = bank;
    }

    public CardInformation(int driverId, int accountNumber, String IBAN, String bank) {
        this.driverId = driverId;
        this.accountNumber = accountNumber;
        this.IBAN = IBAN;
        this.bank = bank;
    }

    public CardInformation(int cardId, int driverId, int accountNumber, String IBAN, String bank) {
        this.cardId = cardId;
        this.driverId = driverId;
        this.accountNumber = accountNumber;
        this.IBAN = IBAN;
        this.bank = bank;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Override
    public String toString() {
        return "CardInformation{" + "cardId=" + cardId + ", driverId=" + driverId + ", accountNumber=" + accountNumber + ", IBAN=" + IBAN + ", bank=" + bank + '}';
    }

}
