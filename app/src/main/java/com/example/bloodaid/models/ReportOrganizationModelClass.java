package com.example.bloodaid.models;

public class ReportOrganizationModelClass {
    private Integer ReportOrganizationId;
    private String Name;
    private String Mobile;
    private String District;
    private String Email;
    private Integer ReportCount;

    public Integer getReportOrganizationId() {
        return ReportOrganizationId;
    }

    public void setReportOrganizationId(Integer reportOrganizationId) {
        ReportOrganizationId = reportOrganizationId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getReportCount() {
        return ReportCount;
    }

    public void setReportCount(Integer reportCount) {
        ReportCount = reportCount;
    }
}
