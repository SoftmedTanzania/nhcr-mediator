package tz.go.moh.him.nhcr.mediator.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents an EMR message.
 */
public class EmrClientsRegistrationAndUpdatesMessage {

    /**
     * The sending facility.
     */
    @JsonProperty("SendingFacility")
    private String sendingFacility;

    /**
     * The sending facility OID.
     */
    @JsonProperty("OID")
    private String oid;

    /**
     * The sending facility security token.
     */
    @JsonProperty("securityToken")
    private String token;

    /**
     * The facility HFR code.
     */
    @JsonProperty("FacilityHFRCode")
    private String facilityHfrCode;


    /**
     * The client medical registration number.
     */
    @JsonProperty("mrn")
    private String mrn;

    /**
     * The client medical registration number.
     */
    @JsonProperty("PostOrUpdate")
    private PostOrUpdate postOrUpdate;

    /**
     * Client's first name
     */
    @JsonProperty("firstame")
    private String firstName;

    /**
     * Client's middle name
     */
    @JsonProperty("middlename")
    private String middleName;

    /**
     * Client's last name
     */
    @JsonProperty("lastname")
    private String lastName;

    /**
     * Client's other name
     */
    @JsonProperty("othername")
    private String otherName;

    /**
     * Client's uln
     */
    @JsonProperty("uln")
    private String uln;

    /**
     * Client's ids list
     */
    @JsonProperty("ids")
    private List<Id> ids;

    /**
     * Client's program ids list
     */
    @JsonProperty("programs")
    private List<Program> programs;

    /**
     * Client's insurance
     */
    @JsonProperty("insurance")
    private Insurance insurance;

    /**
     * Client's permanent Address
     */
    @JsonProperty("permanent_address")
    private Address permanentAddress;

    /**
     * Client's residential Address
     */
    @JsonProperty("residential_address")
    private Address residentialAddress;

    /**
     * Client's Place of Birth
     */
    @JsonProperty("place_of_birth")
    private Address placeOfBirth;

    /**
     * Client's Phone number country code
     */
    @JsonProperty("country_code")
    private String countryCode;

    /**
     * Client's Phone number
     */
    @JsonProperty("phone_number")
    private String phoneNumber;

    /**
     * Client's Family Linkage e.g Parent, Sibling e.t.c
     */
    @JsonProperty("family_linkages")
    private Linkage familyLinkages;

    /**
     * Client's Other Linkage Next of Kin
     */
    @JsonProperty("other_linkages")
    private Linkage otherLinkages;

    /**
     * Client's Sex
     */
    @JsonProperty("sex")
    private String sex;

    /**
     * Client's Date of Birth
     */
    @JsonProperty("dob")
    private String dob;

    /**
     * HFR code of the facility that originally encountered
     */
    @JsonProperty("place_encountered")
    private String placeEncountered;

    /**
     * Status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Date createdAt
     */
    @JsonProperty("createdAt")
    private String createdAt;


    /**
     * Initializes a new instance of the {@link EmrClientsRegistrationAndUpdatesMessage} class.
     */
    public EmrClientsRegistrationAndUpdatesMessage() {
    }

    /**
     * Initializes a new instance of the {@link EmrClientsRegistrationAndUpdatesMessage} class.
     *
     * @param sendingFacility The sending facility.
     * @param facilityHfrCode The facility HFR code.
     */
    public EmrClientsRegistrationAndUpdatesMessage(String sendingFacility, String facilityHfrCode) {
        this();
        this.setSendingFacility(sendingFacility);
        this.setFacilityHfrCode(facilityHfrCode);
    }

    /**
     * Gets the sending facility.
     *
     * @return Returns the sending facliity.
     */
    public String getSendingFacility() {
        return sendingFacility;
    }

    /**
     * Sets the sending facility.
     *
     * @param sendingFacility The sending facility.
     */
    public void setSendingFacility(String sendingFacility) {
        this.sendingFacility = sendingFacility;
    }

    /**
     * Gets the facility HFR code.
     *
     * @return Returns the facility HFR code.
     */
    public String getFacilityHfrCode() {
        return facilityHfrCode;
    }

    /**
     * Sets the facility HFR code.
     *
     * @param facilityHfrCode The facility HFR code.
     */
    public void setFacilityHfrCode(String facilityHfrCode) {
        this.facilityHfrCode = facilityHfrCode;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public List<Id> getIds() {
        return ids;
    }

    public void setIds(List<Id> ids) {
        this.ids = ids;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Address getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(Address permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public Address getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(Address residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public Address getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(Address placeOfBirth) {
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

    public Linkage getFamilyLinkages() {
        return familyLinkages;
    }

    public void setFamilyLinkages(Linkage familyLinkages) {
        this.familyLinkages = familyLinkages;
    }

    public Linkage getOtherLinkages() {
        return otherLinkages;
    }

    public void setOtherLinkages(Linkage otherLinkages) {
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
        POST('P'), UPDATE('U');

        private final Character value;

        PostOrUpdate(Character value) {
            this.value = value;
        }

        public Character getValue() {
            return value;
        }
    }

    public static class Id {
        /**
         * Id Value
         */
        private String id;

        /**
         * Id Type
         */
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Program {
        /**
         * Id of the client within the Program
         */
        private String id;

        /**
         * Name of the Program matching the id
         */
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Insurance {
        /**
         * Insurance Id of the client
         */
        private String id;

        /**
         * Name of the Insurance
         */
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Address {
        /**
         * Name of the Region
         */
        private String region;

        /**
         * Name of the District Council
         */
        private String council;

        /**
         * Name of the Ward
         */
        private String ward;

        /**
         * Name of the Village
         */
        private String village;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCouncil() {
            return council;
        }

        public void setCouncil(String council) {
            this.council = council;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }
    }

    public static class Linkage {
        /**
         * Id value of the Linked client
         */
        private String id;

        /**
         * Issuing authority name of the Id
         */
        private String sourceOfId;

        /**
         * Type of Linkage
         */
        private String typeOfLinkage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSourceOfId() {
            return sourceOfId;
        }

        public void setSourceOfId(String sourceOfId) {
            this.sourceOfId = sourceOfId;
        }

        public String getTypeOfLinkage() {
            return typeOfLinkage;
        }

        public void setTypeOfLinkage(String typeOfLinkage) {
            this.typeOfLinkage = typeOfLinkage;
        }
    }
}
