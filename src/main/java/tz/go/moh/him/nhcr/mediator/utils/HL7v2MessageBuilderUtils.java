package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.IN1;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientId;
import tz.go.moh.him.nhcr.mediator.domain.ClientProgram;
import tz.go.moh.him.nhcr.mediator.hl7v2.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.hl7v2.segment.ZXT;

import java.io.IOException;
import java.util.Calendar;

/**
 * Represents an HL7v2 message builder utility.
 */
public class HL7v2MessageBuilderUtils {

    /**
     * Creates an ADT A04 message.
     *
     * @return Returns the created ADT A04 message.
     */
    public static ADT_A04 createAdtA04() {
        return new ADT_A04();
    }

    /**
     * Creates an ZXT A01 message.
     *
     * @param sendingFacility      The sending facility.
     * @param facilityHfrCode      The facility HFR code.
     * @param facilityOid          The sending facility OID.
     * @param receivingFacility    The receiving facility.
     * @param receivingApplication The receiving Application.
     * @param securityAccessToken  The sending facility security token.
     * @param client               the client registered
     * @return Returns the created ZXT A01 message.
     */
    public static ZXT_A01 createZxtA01(String sendingFacility, String facilityHfrCode, String facilityOid, String receivingFacility, String receivingApplication, String securityAccessToken, Client client) throws IOException, HL7Exception {
        ZXT_A01 adt = new ZXT_A01();
        adt.initQuickstart("ADT", "A01", "P");

        //The ZXT Segment
        ZXT zxt = adt.getZXT();

        /*
         * Populate the MSH Segment
         */
        MSH mshSegment = adt.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue(facilityOid);
        mshSegment.getSendingFacility().getNamespaceID().setValue(facilityHfrCode);
        mshSegment.getReceivingApplication().getNamespaceID().setValue(receivingApplication);
        mshSegment.getReceivingFacility().getNamespaceID().setValue(receivingFacility);
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(Calendar.getInstance());
        mshSegment.getSecurity().setValue(securityAccessToken);
        mshSegment.getVersionID().getVersionID().setValue("2.3.1");


        /*
         * Populate the EVN Segment
         */
        EVN evnSegment = adt.getEVN();
        evnSegment.getRecordedDateTime().getTimeOfAnEvent().setValue(Calendar.getInstance());


        /*
         * Populate the PID Segment
         */
        PID pid = adt.getPID();

        // Populating the PID.3
        for (int i = 0; i < client.getPrograms().size(); i++) {
            //Populating the client ids.
            ClientProgram clientProgram = client.getPrograms().get(i);
            pid.getPatientIdentifierList(i).getID().setValue(clientProgram.getId());
            pid.getPatientIdentifierList(i).getAssigningAuthority().getNamespaceID().setValue(clientProgram.getName());
            pid.getPatientIdentifierList(i).getAssigningFacility().getNamespaceID().setValue(facilityHfrCode);
        }

        //Populating the client names.
        pid.getPatientName(0).getFamilyLastName().getFamilyName().setValue(client.getLastName());
        pid.getPatientName(0).getGivenName().setValue(client.getFirstName());
        pid.getPatientName(0).getMiddleInitialOrName().setValue(client.getMiddleName());
        pid.getPatientName(0).getNameTypeCode().setValue("L");

        //Populating the client date of birth.
        pid.getDateTimeOfBirth().getTimeOfAnEvent().setValue(Utils.checkDateFormatStrings(client.getDob()));

        //Populating the client sex.
        if (client.getSex().equalsIgnoreCase("male")) {
            pid.getSex().setValue("M");
        } else if (client.getSex().equalsIgnoreCase("female")) {
            pid.getSex().setValue("F");
        } else {
            throw new ArgumentException();
        }
        //Populating other client's names.
        pid.getPatientAlias(0).getGivenName().setValue(client.getOtherName());

        int patientAddressIndex = 0;
        if (client.getResidentialAddress() != null) {
            //Populating Residential Address
            String councilWardVillage = client.getResidentialAddress().getCouncil() + "*" + client.getResidentialAddress().getWard() + "*" + client.getResidentialAddress().getVillage();
            pid.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pid.getPatientAddress(patientAddressIndex).getCity().setValue(client.getResidentialAddress().getRegion());
            pid.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getResidentialAddress().getRegion());
            pid.getPatientAddress(patientAddressIndex).getAddressType().setValue("H");
            patientAddressIndex++;
        }

        if (client.getPermanentAddress() != null) {
            //Populating Permanent Address
            String councilWardVillage = client.getPermanentAddress().getCouncil() + "*" + client.getPermanentAddress().getWard() + "*" + client.getPermanentAddress().getVillage();
            pid.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pid.getPatientAddress(patientAddressIndex).getCity().setValue(client.getPermanentAddress().getRegion());
            pid.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getPermanentAddress().getRegion());
            pid.getPatientAddress(patientAddressIndex).getAddressType().setValue("P");
        }

        //Populating phone number
        pid.getPhoneNumberHome(0).getTelecommunicationUseCode().setValue("PRN");
        pid.getPhoneNumberHome(0).getTelecommunicationEquipmentType().setValue("PH");
        pid.getPhoneNumberHome(0).getAreaCityCode().setValue(client.getCountryCode());
        pid.getPhoneNumberHome(0).getPhoneNumber().setValue(client.getPhoneNumber());

        //Populating uln
        pid.getSSNNumberPatient().setValue(client.getUln());

        for (ClientId clientId : client.getIds()) {
            if (clientId.getType().equalsIgnoreCase("DRIVERS_LICENSE_ID")) {
                //Populating drivers license
                pid.getDriverSLicenseNumberPatient().getDriverSLicenseNumber().setValue(clientId.getId());
            } else if (clientId.getType().equalsIgnoreCase("NATIONAL_ID")) {
                //Populating national ID
                pid.getCitizenship(0).getIdentifier().setValue(clientId.getId());
                pid.getNationality().getIdentifier().setValue(clientId.getId());
            } else if (clientId.getType().equalsIgnoreCase("RITA_ID")) {
                //Populating Rita Birth Certificate ID
                zxt.getRitaId().getId().setValue(clientId.getId());
                zxt.getRitaId().getCountryCode().setValue("TZA");
                zxt.getRitaId().getIdType().setValue("BTH_CRT");
                zxt.getRitaId().getCountryName().setValue("Tanzania");
            }
        }

        /*
         * Populating IN1 Segment
         */
        if (client.getInsurance() != null) {
            IN1 in1Segment = adt.getIN1IN2IN3().getIN1();
            in1Segment.getSetIDIN1().setValue("1");
            in1Segment.getInsuredSIDNumber(0).getID().setValue(client.getInsurance().getId()); //TODO have a discussion on this
            in1Segment.getInsuranceCompanyName(0).getOrganizationName().setValue(client.getInsurance().getName());
        }


        return adt;
    }
}
