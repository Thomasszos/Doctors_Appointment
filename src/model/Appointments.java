package model;

public class Appointments {
    // properties
    private String appointmentId;
    private String appointmentDoctor;
    private String appointmentDate;

    // constructor
    public Appointments(String appointmentId, String appointmentDoctor, String appointmentDate  ) {
        this.appointmentId = appointmentId;
        this.appointmentDoctor = appointmentDoctor;
        this.appointmentDate = appointmentDate;
    }


    // methods
    public void setAppointmentId(String aid) {
        this.appointmentId = aid;
    }

    public void setAppointmentDoctor(String ad) {
        this.appointmentDoctor = ad;
    }

    public void setAppointmentDate(String adate) {
        this.appointmentDate = adate;
    }

    public String getAppointmentId() {
        return this.appointmentId;
    }

    public String getAppointmentDoctor() {
        return this.appointmentDoctor;
    }

    public String getAppointmentDate() {
        return this.appointmentDate;
    }
}
