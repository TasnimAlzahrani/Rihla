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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="served_neighborhood")
public class ServedNeighborhood implements java.io.Serializable {

    @Id
    @Column(name = "neighborhoodId")
    private String neighborhoodId;

    @Id
    @Column(name = "driverId")
    private int driverId;

    public String getNeighborhoodId() {
        return neighborhoodId;
    }

    public void setNeighborhoodId(String neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public ServedNeighborhood(String neighborhoodId, int driverId) {
        this.neighborhoodId = neighborhoodId;
        this.driverId = driverId;
    }

    public ServedNeighborhood() {
    }

    @Override
    public String toString() {
        return "ServedNeighborhood{" + "neighborhoodId=" + neighborhoodId + ", driverId=" + driverId + '}';
    }

}
