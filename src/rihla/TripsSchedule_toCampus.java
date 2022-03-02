/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author HU6EM001
 */
@Entity
@Table(name = "tripsschedule_tocampus")
public class TripsSchedule_toCampus implements Serializable {

    @Id
    @Column(name = "toCampusTime")
    private String toCampusTime;

    @Column(name = "busId")
    private int busId;

    public TripsSchedule_toCampus() {
    }

    public String getToCampusTime() {
        return toCampusTime;
    }

    public void setToCampusTime(String toCampusTime) {
        this.toCampusTime = toCampusTime;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public TripsSchedule_toCampus(String toCampusTime, int busId) {
        this.toCampusTime = toCampusTime;
        this.busId = busId;
    }

}
