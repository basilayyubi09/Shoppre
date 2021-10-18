package com.peceinfotech.shoppre.AuthenticationModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeResponse {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_description")
    @Expose
    private String errorDescription;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("salutation")
    @Expose
    private Object salutation;
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
    private Object alternateEmail;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("secondary_phone")
    @Expose
    private Object secondaryPhone;
    @SerializedName("profile_photo_url")
    @Expose
    private Object profilePhotoUrl;
    @SerializedName("survey")
    @Expose
    private Object survey;
    @SerializedName("virtual_address_code")
    @Expose
    private String virtualAddressCode;
    @SerializedName("is_courier_migrated")
    @Expose
    private String isCourierMigrated;
    @SerializedName("is_member")
    @Expose
    private Integer isMember;
    @SerializedName("membership_validity")
    @Expose
    private Object membershipValidity;
    @SerializedName("membership_start_date")
    @Expose
    private Object membershipStartDate;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
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

    public Object getSalutation() {
        return salutation;
    }

    public void setSalutation(Object salutation) {
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

    public Object getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(Object alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(Object secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public Object getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(Object profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Object getSurvey() {
        return survey;
    }

    public void setSurvey(Object survey) {
        this.survey = survey;
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
