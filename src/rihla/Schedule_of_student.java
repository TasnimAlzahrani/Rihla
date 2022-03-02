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
 * @author HU6EM001
 */
@Entity
@Table(name = "schedule_of_student")
public class Schedule_of_student implements Serializable {

    //scheduleId, studentId, day_of_week, start_time, end_time
//    @Id
//    @Column(name = "scheduleId")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int scheduleId;
    @Id
    @Column(name = "studentId")
    private int studentId;

    @Id
    @Column(name = "day_of_week")
    private String day_of_week;

    @Column(name = "start_time")
    private String start_time;

    @Column(name = "end_time")
    private String end_time;

    @Column(name = "start_state")
    private String start_state;

    @Column(name = "end_state")
    private String end_state;

    public Schedule_of_student() {
    }

    public Schedule_of_student(String day_of_week, String start_time, String end_time, String start_state, String end_state) {
        this.day_of_week = day_of_week;
        this.start_time = start_time;
        this.end_time = end_time;
        this.start_state = start_state;
        this.end_state = end_state;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_state() {
        return start_state;
    }

    public void setStart_state(String start_state) {
        this.start_state = start_state;
    }

    public String getEnd_state() {
        return end_state;
    }

    public void setEnd_state(String end_state) {
        this.end_state = end_state;
    }

}
