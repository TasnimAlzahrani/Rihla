package rihla;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author Tasneem
 */
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    @Id
    @Column(name = "driverId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    @Column(name = "busId")
    private int busId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "servedCity")
    private String servedCity;

    @Column(name = "fee")
    private double fee;

    @Column(name = "dateOfbirth")
    private String dateOfbirth;

    @Column(name = "nationality")
    private String nationality;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "phone_number")
    private String phone_number;

    public Driver() {
    }

    public Driver(int driverId, int busId, String name, String email, String password, String servedCity, double fee, String dateOfbirth, String nationality, byte[] photo, String phone_number) {
        this.driverId = driverId;
        this.busId = busId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.servedCity = servedCity;
        this.fee = fee;
        this.dateOfbirth = dateOfbirth;
        this.nationality = nationality;
        this.photo = photo;
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServedCity() {
        return servedCity;
    }

    public void setServedCity(String servedCity) {
        this.servedCity = servedCity;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public String getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(String dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return name;
    }

}
