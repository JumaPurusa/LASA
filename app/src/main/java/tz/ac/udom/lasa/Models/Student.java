package tz.ac.udom.lasa.Models;

public class Student {

    private String name, course, profilePhoto, regNo, accountNo, sponsor;
    private int yearOfStudy, isRegistered, isBeneficiary;

    public Student(){

    }

    public Student(String name, String course, String profilePhoto, String regNo,
                   String accountNo, String sponsor, int yearOfStudy, int isRegistered, int isBeneficiary) {
        this.name = name;
        this.course = course;
        this.profilePhoto = profilePhoto;
        this.regNo = regNo;
        this.accountNo = accountNo;
        this.sponsor = sponsor;
        this.yearOfStudy = yearOfStudy;
        this.isRegistered = isRegistered;
        this.isBeneficiary = isBeneficiary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(int isRegistered) {
        this.isRegistered = isRegistered;
    }

    public int getIsBeneficiary() {
        return isBeneficiary;
    }

    public void setIsBeneficiary(int isBeneficiary) {
        this.isBeneficiary = isBeneficiary;
    }
}
