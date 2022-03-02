/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Eman Alharbi
 */
@Entity
@Table(name = "student")

public class Student implements Serializable {

    @Id
    @Column(name = "studentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "convenientPrice")
    private double convenientPrice;

    @Column(name = "driverId")
    private Integer driverId;

    @Column(name = "zipcode")
    private int zipcode;

    public Student() {
    }

    public Student(int studentId, String name, String email, String password, String phoneNumber, double convenientPrice, int driverId, int zipcode) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.convenientPrice = convenientPrice;
        this.driverId = driverId;
        this.zipcode = zipcode;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getConvenientPrice() {
        return convenientPrice;
    }

    public void setConvenientPrice(double convenientPrice) {
        this.convenientPrice = convenientPrice;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

}
