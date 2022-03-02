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
@Table(name = "student_address")
public class StudentAddress implements Serializable {

    @Id
    @Column(name = "addressId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int zipcode;
    
    @Column(name = "neighborhood")
    private String neighborhood;
    
    @Column(name = "street")
    private String street;
    
    @Column(name = "city")
    private String city;

    public StudentAddress() {
    }

    public StudentAddress(int zipcode, String neighborhood, String street, String city) {
        this.zipcode = zipcode;
        this.neighborhood = neighborhood;
        this.street = street;
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return this.city+", "+this.neighborhood+", "+this.street; //To change body of generated methods, choose Tools | Templates.
    } 

}
