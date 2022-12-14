package com.shoppreglobal.shoppre.AccountResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeResponse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alternate_email")
    @Expose
    private String alternateEmail;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("secondary_phone")
    @Expose
    private String secondaryPhone;
    @SerializedName("profile_photo_url")
    @Expose
    private String profilePhotoUrl;
    @SerializedName("survey")
    @Expose
    private String survey;
    @SerializedName("is_virtual_address_unlock")
    @Expose
    private Integer isVirtualAddressUnlock;
    @SerializedName("virtual_address_code")
    @Expose
    private String virtualAddressCode;
    @SerializedName("is_courier_migrated")
    @Expose
    private String isCourierMigrated;
    @SerializedName("is_member")
    @Expose
    private Integer isMember;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("membership_validity")
    @Expose
    private Object membershipValidity;
    @SerializedName("membership_start_date")
    @Expose
    private Object membershipStartDate;
    @SerializedName("is_email_verified")
    @Expose
    private Integer isEmailVerified;

    public Integer getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Integer isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getSurvey() {
        return survey;
    }

    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public Integer getIsVirtualAddressUnlock() {
        return isVirtualAddressUnlock;
    }

    public void setIsVirtualAddressUnlock(Integer isVirtualAddressUnlock) {
        this.isVirtualAddressUnlock = isVirtualAddressUnlock;
    }

    public String getVirtualAddressCode() {
        return virtualAddressCode;
    }

    public void setVirtualAddressCode(String virtualAddressCode) {
        this.virtualAddressCode = virtualAddressCode;
    }

    public String getIsCourierMigrated() {
        return isCourierMigrated;
    }

    public void setIsCourierMigrated(String isCourierMigrated) {
        this.isCourierMigrated = isCourierMigrated;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getMembershipValidity() {
        return membershipValidity;
    }

    public void setMembershipValidity(Object membershipValidity) {
        this.membershipValidity = membershipValidity;
    }

    public Object getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(Object membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

}
