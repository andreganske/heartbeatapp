package com.andreganske.heartbeat.db;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andre on 04/07/2016.
 */
public class Heartbeat {

    private static final String TAG = "Heartbeat";

    private Integer id;
    private Integer systolic;
    private Integer diastolic;
    private String createdAt;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Heartbeat () {
        super();
    }

    public Heartbeat(Integer systolic, Integer diastolic) {
        super();
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.createdAt = formatter.format(new Date());
    }

    public Heartbeat(Integer id, Integer systolic, Integer diastolic, String createdAt) {
        super();
        this.id = id;
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Heartbeat)) return false;

        Heartbeat heartbeat = (Heartbeat) o;

        if (!getId().equals(heartbeat.getId())) return false;
        if (!getSystolic().equals(heartbeat.getSystolic())) return false;
        if (!getDiastolic().equals(heartbeat.getDiastolic())) return false;
        return getCreatedAt().equals(heartbeat.getCreatedAt());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getSystolic().hashCode();
        result = 31 * result + getDiastolic().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "id=" + id +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", created_at=" + createdAt +
                '}';
    }
}

