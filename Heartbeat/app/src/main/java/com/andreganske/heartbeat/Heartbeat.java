package com.andreganske.heartbeat;

import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.logging.SimpleFormatter;

/**
 * Created by Andre on 04/07/2016.
 */
public class Heartbeat {

    private static final String TAG = "Heartbeat";

    private Integer systolic;
    private Integer diastolic;
    private String createdAt;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    Heartbeat(Integer systolic, Integer diastolic) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.createdAt = formatter.format(new Date());
    }

    Heartbeat(Integer systolic, Integer diastolic, String createdAt) {
        this.diastolic = diastolic;
        this.systolic = systolic;
        this.createdAt = createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Heartbeat)) return false;

        Heartbeat heartbeat = (Heartbeat) o;

        if (!getDiastolic().equals(heartbeat.getDiastolic())) return false;
        if (!getSystolic().equals(heartbeat.getSystolic())) return false;
        return getCreatedAt().equals(heartbeat.getCreatedAt());

    }

    @Override
    public int hashCode() {
        int result = getDiastolic().hashCode();
        result = 31 * result + getSystolic().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Heartbeat{" +
                "diastolic=" + diastolic +
                ", systolic=" + systolic +
                ", created_at=" + createdAt +
                '}';
    }
}

