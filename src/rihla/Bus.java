/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rihla;

/**
 *
 * @author
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus")
public class Bus implements java.io.Serializable {

    @Id
    @Column(name = "busId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busId;

    @Column(name = "licenseNumber")
    private int licenseNumber;

    @Column(name = "plateNumber")
    private String plateNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "owner")
    private String owner;

    @Column(name = "gateNumber")
    private String gateNumber;

    
    public Bus() {
    }

    public Bus(int busId, int licenseNumber, String plateNumber, int capacity, String owner, String gateNumber) {

        this.busId = busId;
        this.licenseNumber = licenseNumber;
        this.plateNumber = plateNumber;
        this.capacity = capacity;
        this.owner = owner;
        this.gateNumber = gateNumber;
    }

    public int getBusId() {
        return this.busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getLicenseNumber() {
        return this.licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPlateNumber() {
        return this.plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGateNumber() {
        return this.gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

}
