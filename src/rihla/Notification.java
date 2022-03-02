/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author HU6EM001
 */
@Entity
@Table(name = " notification ")
public class Notification implements java.io.Serializable {

    @Id
    @Column(name = "notificationId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;
    @Column(name = "driverId")
    private int driverId;

    @Column(name = "notificationcontent")
    private String notificationcontent;

    @Column(name = "ApproximatedTime")
    private String ApproximatedTime;

    public Notification() {
    }

    public Notification(int notificationId, int driverId, String notificationcontent, String ApproximatedTime) {
        this.notificationId = notificationId;
        this.driverId = driverId;
        this.notificationcontent = notificationcontent;
        this.ApproximatedTime = ApproximatedTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getApproximatedTime() {
        return ApproximatedTime;
    }

    public void setApproximatedTime(String ApproximatedTime) {
        this.ApproximatedTime = ApproximatedTime;
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

    public String getNotificationcontent() {
        return notificationcontent;
    }

    public void setNotificationcontent(String notificationcontent) {
        this.notificationcontent = notificationcontent;
    }

}
