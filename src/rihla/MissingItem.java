/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

/**
 *
 * @author HU6EM001
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = " missingitem ")
public class MissingItem implements Serializable {

    @Id
    @Column(name = "missingItemId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;
    
    @Column(name = "driverId")
    private int driverId;
    
    @Column(name = "missingItemLabel")
    private String missingItemLabel;
    
    @Column(name = "missingItemDescription")
    private String missingItemDescription;

    public MissingItem() {
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMissingItemLabel() {
        return missingItemLabel;
    }

    public void setMissingItemLabel(String missingItemLabel) {
        this.missingItemLabel = missingItemLabel;
    }

    public String getMissingItemDescription() {
        return missingItemDescription;
    }

    public void setMissingItemDescription(String missingItemDescription) {
        this.missingItemDescription = missingItemDescription;
    }

    public MissingItem(int notificationId, int driverId, String missingItemLabel, String missingItemDescription) {
        this.notificationId = notificationId;
        this.driverId = driverId;
        this.missingItemLabel = missingItemLabel;
        this.missingItemDescription = missingItemDescription;
    }

}
