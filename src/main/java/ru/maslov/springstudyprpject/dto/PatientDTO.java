package ru.maslov.springstudyprpject.dto;

public class PatientDTO extends UserDTO {
    private String policyNumber;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }
}
