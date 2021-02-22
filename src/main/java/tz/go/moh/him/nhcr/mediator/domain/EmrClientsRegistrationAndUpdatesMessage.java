package tz.go.moh.him.nhcr.mediator.domain;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents an EMR EmrClientsRegistrationAndUpdates message.
 */
public class EmrClientsRegistrationAndUpdatesMessage extends EmrMessage {
    /**
     * The registered or updated clients list.
     */
    @SerializedName("clients")
    private List<Client> clients;

    /**
     * Gets the registered or updated clients list.
     *
     * @return the registered or updated clients list.
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Sets the registered or updated clients list.
     *
     * @param clients Sets the registered or updated clients list.
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
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

    public static class Client {
        /**
         * The client medical registration number.
         */
        @SerializedName("mrn")
        private String mrn;

        /**
         * The client medical registration number.
         */
        @SerializedName("PostOrUpdate")
        private PostOrUpdate postOrUpdate;

        /**
         * Client's first name
         */
        @SerializedName("firstname")
        private String firstName;

        /**
         * Client's middle name
         */
        @SerializedName("middlename")
        private String middleName;

        /**
         * Client's last name
         */
        @SerializedName("lastname")
        private String lastName;

        /**
         * Client's other name
         */
        @SerializedName("othername")
        private String otherName;

        /**
         * Client's uln
         */
        @SerializedName("uln")
        private String uln;

        /**
         * Client's ids list
         */
        @SerializedName("ids")
        private List<Id> ids;

        /**
         * Client's program ids list
         */
        @SerializedName("programs")
        private List<Program> programs;

        /**
         * Client's insurance
         */
        @SerializedName("insurance")
        private Insurance insurance;

        /**
         * Client's permanent Address
         */
        @SerializedName("permanent_address")
        private Address permanentAddress;

        /**
         * Client's residential Address
         */
        @SerializedName("residential_address")
        private Address residentialAddress;

        /**
         * Client's Place of Birth
         */
        @SerializedName("place_of_birth")
        private Address placeOfBirth;

        /**
         * Client's Phone number country code
         */
        @SerializedName("country_code")
        private String countryCode;

        /**
         * Client's Phone number
         */
        @SerializedName("phone_number")
        private String phoneNumber;

        /**
         * Client's Family Linkage e.g Parent, Sibling e.t.c
         */
        @SerializedName("family_linkages")
        private Linkage familyLinkages;

        /**
         * Client's Other Linkage Next of Kin
         */
        @SerializedName("other_linkages")
        private Linkage otherLinkages;

        /**
         * Client's Sex
         */
        @SerializedName("sex")
        private String sex;

        /**
         * Client's Date of Birth
         */
        @SerializedName("dob")
        private String dob;

        /**
         * HFR code of the facility that originally encountered
         */
        @SerializedName("place_encountered")
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
        @SerializedName("id")
        private String id;

        /**
         * Issuing authority name of the Id
         */
        @SerializedName("source_of_id")
        private String sourceOfId;

        /**
         * Type of Linkage
         */
        @SerializedName("type_of_linkage")
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
