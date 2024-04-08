package viewmodel;

import model.Appointments;

public class AppointmentViewModel {
    private Appointments appointment;

    public AppointmentViewModel(String appointmentId, String appointmentDoctor, String appointmentDate ) {
        appointment = new Appointments(appointmentId,appointmentDoctor,appointmentDate);
    }

    public String getAppointmentId() {
        return this.appointment.getAppointmentId();
    }

    public String getAppointmentDoctor() {
        return this.appointment.getAppointmentDoctor();
    }

    public String getAppointmentDate(){
        return this.appointment.getAppointmentDate();
    }

    public void setAppointmentId(String nid) {
        this.appointment.setAppointmentId(nid);
    }

    public void setAppointmentDoctor(String nad) {
        this.appointment.setAppointmentDoctor(nad);
    }

    public void setAppointmentDate(String nd) {
        this.appointment.setAppointmentDate(nd);
    }

}
