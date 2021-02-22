package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Client {
    /**
     * The client medical registration number.
     */
    @SerializedName("mrn")
    @JsonProperty("mrn")
    private String mrn;

    /**
     * The client medical registration number.
     */
    @SerializedName("postOrUpdate")
    @JsonProperty("postOrUpdate")
    private PostOrUpdate postOrUpdate;

    /**
     * Client's first name
     */
    @SerializedName("firstName")
    @JsonProperty("firstName")
    private String firstName;

    /**
     * Client's middle name
     */
    @SerializedName("middleName")
    @JsonProperty("middleName")
    private String middleName;

    /**
     * Client's last name
     */
    @SerializedName("lastName")
    @JsonProperty("lastName")
    private String lastName;

    /**
     * Client's other name
     */
    @SerializedName("otherName")
    @JsonProperty("otherName")
    private String otherName;

    /**
     * Client's uln
     */
    @SerializedName("uln")
    @JsonProperty("uln")
    private String uln;

    /**
     * Client's ids list
     */
    @SerializedName("ids")
    @JsonProperty("ids")
    private List<ClientId> ids;

    /**
     * Client's program ids list
     */
    @SerializedName("programs")
    @JsonProperty("programs")
    private List<ClientProgram> programs;

    /**
     * Client's insurance
     */
    @SerializedName("insurance")
    @JsonProperty("insurance")
    private ClientInsurance clientInsurance;

    /**
     * Client's permanent Address
     */
    @SerializedName("permanentAddress")
    @JsonProperty("permanentAddress")
    private ClientAddress permanentClientAddress;

    /**
     * Client's residential Address
     */
    @SerializedName("residentialAddress")
    @JsonProperty("residentialAddress")
    private ClientAddress residentialClientAddress;

    /**
     * Client's Place of Birth
     */
    @SerializedName("placeOfBirth")
    @JsonProperty("placeOfBirth")
    private ClientAddress placeOfBirth;

    /**
     * Client's Phone number country code
     */
    @SerializedName("countryCode")
    @JsonProperty("countryCode")
    private String countryCode;

    /**
     * Client's Phone number
     */
    @SerializedName("phoneNumber")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    /**
     * Client's Family Linkage e.g Parent, Sibling e.t.c
     */
    @SerializedName("familyLinkages")
    @JsonProperty("familyLinkages")
    private ClientLinkage familyLinkages;

    /**
     * Client's Other Linkage Next of Kin
     */
    @SerializedName("otherLinkages")
    @JsonProperty("otherLinkages")
    private ClientLinkage otherLinkages;

    /**
     * Client's Sex
     */
    @SerializedName("sex")
    @JsonProperty("sex")
    private String sex;

    /**
     * Client's Date of Birth
     */
    @SerializedName("dob")
    @JsonProperty("dob")
    private String dob;

    /**
     * HFR code of the facility that originally encountered
     */
    @SerializedName("placeEncountered")
    @JsonProperty("placeEncountered")
    private String placeEncountered;

    /**
     * Status
     */
    @SerializedName("status")
    private String status;

    /**
     * Date createdAt
     */
    @SerializedName("createdAt")
    private String createdAt;

    public String getMrn() {
        return mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public PostOrUpdate getPostOrUpdate() {
        return postOrUpdate;
    }

    public void setPostOrUpdate(PostOrUpdate postOrUpdate) {
        this.postOrUpdate = postOrUpdate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getUln() {
        return uln;
    }

    public void setUln(String uln) {
        this.uln = uln;
    }

    public List<ClientId> getIds() {
        return ids;
    }

    public void setIds(List<ClientId> ids) {
        this.ids = ids;
    }

    public List<ClientProgram> getPrograms() {
        return programs;
    }

    public void setPrograms(List<ClientProgram> programs) {
        this.programs = programs;
    }

    public ClientInsurance getInsurance() {
        return clientInsurance;
    }

    public void setInsurance(ClientInsurance clientInsurance) {
        this.clientInsurance = clientInsurance;
    }

    public ClientAddress getPermanentAddress() {
        return permanentClientAddress;
    }

    public void setPermanentAddress(ClientAddress permanentClientAddress) {
        this.permanentClientAddress = permanentClientAddress;
    }

    public ClientAddress getResidentialAddress() {
        return residentialClientAddress;
    }

    public void setResidentialAddress(ClientAddress residentialClientAddress) {
        this.residentialClientAddress = residentialClientAddress;
    }

    public ClientAddress getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(ClientAddress placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ClientLinkage getFamilyLinkages() {
        return familyLinkages;
    }

    public void setFamilyLinkages(ClientLinkage familyLinkages) {
        this.familyLinkages = familyLinkages;
    }

    public ClientLinkage getOtherLinkages() {
        return otherLinkages;
    }

    public void setOtherLinkages(ClientLinkage otherLinkages) {
        this.otherLinkages = otherLinkages;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPlaceEncountered() {
        return placeEncountered;
    }

    public void setPlaceEncountered(String placeEncountered) {
        this.placeEncountered = placeEncountered;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public enum PostOrUpdate {
        POST("P"), UPDATE("U");

        private final String value;

        PostOrUpdate(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}