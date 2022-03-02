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
@Table(name = "tripsschedule_fromcampus")
public class TripsSchedule_fromCampus implements Serializable {
    //fromCampusTime, busId
    @Id
    @Column(name ="fromCampusTime")
    private String fromCampusTime;
    
    @Column(name ="busId")
    private int busId;

    public TripsSchedule_fromCampus() {
    }

    public TripsSchedule_fromCampus(String fromCampusTime, int busId) {
        this.fromCampusTime = fromCampusTime;
        this.busId = busId;
    }

    public String getFromCampusTime() {
        return fromCampusTime;
    }

    public void setFromCampusTime(String fromCampusTime) {
        this.fromCampusTime = fromCampusTime;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }
    
}
