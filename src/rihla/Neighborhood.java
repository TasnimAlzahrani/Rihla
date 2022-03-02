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
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "neighborhood")

public class Neighborhood implements java.io.Serializable {
    
    @Id
    @Column(name = "neighborhoodId")
    private String neighborhoodId;
    @Column(name = "neighborhoodName")
    private String neighborhoodName;

    public Neighborhood() {
    }

    public Neighborhood(String neighborhoodId, String neighborhoodName) {
        this.neighborhoodId = neighborhoodId;
        this.neighborhoodName = neighborhoodName;
    }

    public String getNeighborhoodId() {
        return neighborhoodId;
    }

    public void setNeighborhoodId(String neighborhoodId) {
        this.neighborhoodId = neighborhoodId;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    @Override
    public String toString() {
        return "Neighborhood{" + "neighborhoodId=" + neighborhoodId + ", neighborhoodName=" + neighborhoodName + '}';
    }
    
    

}
