package model;

public class Patients {

    // properties
    private String patientId;
    private String patientFirst;
    private String patientLast;
    private String patientEmail;


    // constructor
    public Patients(String ii,String pf, String pl, String pe) {
        this.patientId =ii;
        this.patientFirst = pf;
        this.patientLast = pl;
        this.patientEmail = pe;
    }

    // methods
    public void setPatientId(String npid){
        this.patientId = npid;
    };

    public void setPatientFirst(String npf) {
        this.patientFirst = npf;
    }

    public void setPatientLast(String npl) {
        this.patientLast = npl;
    }

    public void setPatientEmail (String npe) {
        this.patientEmail = npe;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public String getPatientFirst() {
        return this.patientFirst;
    }

    public String getPatientLast() {
        return this.patientLast;
    };

    public String getPatientEmail() {
        return this.patientEmail;
    }


}
