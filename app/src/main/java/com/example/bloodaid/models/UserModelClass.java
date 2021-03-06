package com.example.bloodaid.models;

public class UserModelClass {
    private Integer UserId;
    private String Name;
    private String Mobile;
    private String Email;
    private String Password;
    private String BloodGroup;
    private String District;
    private Integer DonorStatus;
    private Double Latitude;
    private Double Longitude;
    private Integer AdminStatus;
    private Integer DonateCount;
    private String LastDonate;
    private Integer Status;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public Integer getDonorStatus() {
        return DonorStatus;
    }

    public void setDonorStatus(Integer donorStatus) {
        DonorStatus = donorStatus;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Integer getDonateCount() {
        return DonateCount;
    }

    public void setDonateCount(Integer donateCount) {
        DonateCount = donateCount;
    }

    public String getLastDonate() {
        return LastDonate;
    }

    public void setLastDonate(String lastDonate) {
        LastDonate = lastDonate;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getAdminStatus() {
        return AdminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        AdminStatus = adminStatus;
    }
}
