package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.IN1;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientId;
import tz.go.moh.him.nhcr.mediator.domain.ClientInsurance;
import tz.go.moh.him.nhcr.mediator.domain.ClientProgram;
import tz.go.moh.him.nhcr.mediator.hl7v2.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.hl7v2.segment.ZXT;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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
     * @param sendingApplication   The sending Application.
     * @param facilityHfrCode      The facility HFR code.
     * @param facilityOid          The sending facility OID.
     * @param receivingFacility    The receiving facility.
     * @param receivingApplication The receiving Application.
     * @param securityAccessToken  The sending facility security token.
     * @param messageControlId     The number or other identifier that uniquely identifies the message
     * @param client               The emr client object
     * @return Returns the created ZXT A01 message.
     * @throws IOException  The IOException thrown
     * @throws HL7Exception The HL7Expeption thrown
     */
    public static ZXT_A01 createZxtA01(String sendingApplication, String facilityHfrCode, String facilityOid, String receivingFacility, String receivingApplication, String securityAccessToken, String messageControlId, Client client) throws HL7Exception, IOException {
        ZXT_A01 adt = new ZXT_A01();
        adt.initQuickstart("ADT", "A01", "P");

        //Populating the MSH Segment
        populateMshSegment(adt, sendingApplication, facilityHfrCode, receivingApplication, receivingFacility, Calendar.getInstance().getTime(), securityAccessToken, messageControlId);

        //Populating the EVN Segment
        populateEvnSegment(adt, Calendar.getInstance().getTime());

        //Populating the PID Segment
        populatePidSegment(adt, client, facilityOid);

        //Populating IN1 Segment
        if (client.getInsurance() != null) {
            populateIn1Segment(adt, client.getInsurance());
        }

        String ritaId = null;
        String votersId = null;
        for (ClientId clientId : client.getIds()) {
            if (clientId.getType().equalsIgnoreCase("VOTERS_ID")) {
                votersId = clientId.getId();
            } else if (clientId.getType().equalsIgnoreCase("RITA_ID")) {
                ritaId = clientId.getId();
            }
        }
        //Populating the ZTX Segment
        populateZxtSegment(adt, votersId, ritaId);


        return adt;
    }


    /**
     * Populates the MSH Segment
     *
     * @param zxtA01               The ZXT_A01 message
     * @param sendingApplication   The sending Application.
     * @param sendingFacility      The sending Facility.
     * @param receivingApplication The receiving Application.
     * @param receivingFacility    The receiving Facility.
     * @param dateTimeOfTheMessage The date time of the message.
     * @param security             The security access token
     * @param messageControlId     The message control ID
     * @return The MSH segment
     * @throws DataTypeException The exception thrown
     */
    private static MSH populateMshSegment(ZXT_A01 zxtA01,
                                          String sendingApplication,
                                          String sendingFacility,
                                          String receivingApplication,
                                          String receivingFacility,
                                          Date dateTimeOfTheMessage,
                                          String security,
                                          String messageControlId
    ) throws DataTypeException {
        MSH mshSegment = zxtA01.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue(sendingApplication);
        mshSegment.getSendingFacility().getNamespaceID().setValue(sendingFacility);
        mshSegment.getReceivingApplication().getNamespaceID().setValue(receivingApplication);
        mshSegment.getReceivingFacility().getNamespaceID().setValue(receivingFacility);
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(dateTimeOfTheMessage);
        mshSegment.getSecurity().setValue(security);
        mshSegment.getVersionID().getVersionID().setValue("2.3.1");
        mshSegment.getProcessingID().getProcessingID().setValue("P");
        mshSegment.getMessageControlID().setValue(messageControlId);

        return mshSegment;
    }

    /**
     * Populates the EVN Segment
     *
     * @param zxtA01           The ZXT_A01 message
     * @param recordedDateTime The recorded date time
     * @return The EVN segment
     * @throws DataTypeException The exception thrown
     */
    private static EVN populateEvnSegment(ZXT_A01 zxtA01, Date recordedDateTime) throws DataTypeException {
        EVN evnSegment = zxtA01.getEVN();
        evnSegment.getRecordedDateTime().getTimeOfAnEvent().setValue(recordedDateTime);

        return evnSegment;
    }

    /**
     * @param zxtA01            The ZXT_A01 message
     * @param client            The emr client object
     * @param assigningFacility The Patient Identifier assigning Facility
     * @return The PID segment
     * @throws HL7Exception The exception thrown
     */
    private static PID populatePidSegment(ZXT_A01 zxtA01, Client client, String assigningFacility) throws HL7Exception {
        //The PID segment
        PID pidSegment = zxtA01.getPID();

        // Populating the PID.3
        for (int i = 0; i < client.getPrograms().size(); i++) {
            //Populating the client ids.
            ClientProgram clientProgram = client.getPrograms().get(i);
            pidSegment.getPatientIdentifierList(i).getID().setValue(clientProgram.getId());
            pidSegment.getPatientIdentifierList(i).getAssigningAuthority().getNamespaceID().setValue(clientProgram.getName());
            pidSegment.getPatientIdentifierList(i).getAssigningFacility().getNamespaceID().setValue(assigningFacility);
        }

        //Populating the client names.
        pidSegment.getPatientName(0).getFamilyLastName().getFamilyName().setValue(client.getLastName());
        pidSegment.getPatientName(0).getGivenName().setValue(client.getFirstName());
        pidSegment.getPatientName(0).getMiddleInitialOrName().setValue(client.getMiddleName());
        pidSegment.getPatientName(0).getNameTypeCode().setValue("L");

        //Populating the client date of birth.
        pidSegment.getDateTimeOfBirth().getTimeOfAnEvent().setValue(DateUtils.checkDateFormatStrings(client.getDob()));

        //Populating the client sex.
        if (client.getSex().equalsIgnoreCase("male")) {
            pidSegment.getSex().setValue("M");
        } else if (client.getSex().equalsIgnoreCase("female")) {
            pidSegment.getSex().setValue("F");
        } else {
            throw new ArgumentException();
        }
        //Populating other client's names.
        pidSegment.getPatientAlias(0).getGivenName().setValue(client.getOtherName());

        int patientAddressIndex = 0;
        if (client.getResidentialAddress() != null) {
            //Populating Residential Address
            String councilWardVillage = client.getResidentialAddress().getCouncil() + "*" + client.getResidentialAddress().getWard() + "*" + client.getResidentialAddress().getVillage();
            pidSegment.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pidSegment.getPatientAddress(patientAddressIndex).getCity().setValue(client.getResidentialAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getResidentialAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getAddressType().setValue("H");
            patientAddressIndex++;
        }

        if (client.getPermanentAddress() != null) {
            //Populating Permanent Address
            String councilWardVillage = client.getPermanentAddress().getCouncil() + "*" + client.getPermanentAddress().getWard() + "*" + client.getPermanentAddress().getVillage();
            pidSegment.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pidSegment.getPatientAddress(patientAddressIndex).getCity().setValue(client.getPermanentAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getPermanentAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getAddressType().setValue("P");
        }

        //Populating phone number
        pidSegment.getPhoneNumberHome(0).getTelecommunicationUseCode().setValue("PRN");
        pidSegment.getPhoneNumberHome(0).getTelecommunicationEquipmentType().setValue("PH");
        pidSegment.getPhoneNumberHome(0).getAreaCityCode().setValue(client.getCountryCode());
        pidSegment.getPhoneNumberHome(0).getPhoneNumber().setValue(client.getPhoneNumber());

        //Populating uln
        pidSegment.getSSNNumberPatient().setValue(client.getUln());

        for (ClientId clientId : client.getIds()) {
            if (clientId.getType().equalsIgnoreCase("DRIVERS_LICENSE_ID")) {
                //Populating drivers license
                pidSegment.getDriverSLicenseNumberPatient().getDriverSLicenseNumber().setValue(clientId.getId());
            } else if (clientId.getType().equalsIgnoreCase("NATIONAL_ID")) {
                //Populating national ID
                pidSegment.getCitizenship(0).getIdentifier().setValue(clientId.getId());
                pidSegment.getNationality().getIdentifier().setValue(clientId.getId());
            }
        }

        return pidSegment;
    }

    /**
     * Populates the IN1 Segment
     *
     * @param zxtA01    The ZXT_A01 message
     * @param insurance The client insurance object
     * @return The IN1 Segment
     * @throws DataTypeException The exception thrown
     */
    private static IN1 populateIn1Segment(ZXT_A01 zxtA01, ClientInsurance insurance) throws DataTypeException {
        IN1 in1Segment = zxtA01.getIN1IN2IN3().getIN1();
        in1Segment.getSetIDIN1().setValue("1");
        in1Segment.getInsuredSIDNumber(0).getID().setValue(insurance.getId()); //TODO have a discussion on this
        in1Segment.getInsuranceCompanyName(0).getOrganizationName().setValue(insurance.getName());

        return in1Segment;
    }

    /**
     * Populates the ZXT Segment
     *
     * @param zxtA01  The ZXT_A01 message
     * @param votesId The Client's Voters Id
     * @param ritaId  The Client's Rita ID
     * @return The ZXT Segment
     * @throws HL7Exception The Exception thrown
     */
    private static ZXT populateZxtSegment(ZXT_A01 zxtA01, String votesId, String ritaId) throws HL7Exception {
        ZXT zxtSegment = zxtA01.getZXT();
        if (votesId != null)
            zxtSegment.getVotersId().setValue(votesId);

        if (ritaId != null) {
            zxtSegment.getRitaId().getId().setValue(ritaId);
            zxtSegment.getRitaId().getIdType().setValue("BTH_CRT");
            zxtSegment.getRitaId().getCountryName().setValue("Tanzania");
            zxtSegment.getRitaId().getCountryCode().setValue("TZA");
        }

        return zxtSegment;
    }

    /**
     * Encodes the ZXT_A01 message into a HL7v2 message string
     *
     * @param zxtA01 The ZXT_A01 message
     * @return The HL7v2 encoded string
     * @throws HL7Exception The exception thrown
     */
    public static String encodeZxtA01Message(ZXT_A01 zxtA01) throws HL7Exception {
        HapiContext context = new DefaultHapiContext();

        //Creating a custom model class factory for the custom ZTX_A01 message
        ModelClassFactory cmf = new CustomModelClassFactory("tz.go.moh.him.nhcr.mediator.hl7v2.message");
        context.setModelClassFactory(cmf);

        Parser parser = context.getPipeParser();

        return parser.encode(zxtA01);
    }

    /**
     * Parses the HL7v2 message string to a ZXT_A01 message
     *
     * @param zxtA01Hl7Message The HL7v2 encoded string
     * @return The ZXT_A01 Message Object
     * @throws HL7Exception The exception thrown
     */
    public static ZXT_A01 parseZxtA01Message(String zxtA01Hl7Message) throws HL7Exception {
        HapiContext context = new DefaultHapiContext();

        //Creating a custom model class factory for the custom ZTX_A01 message
        ModelClassFactory cmf = new CustomModelClassFactory("tz.go.moh.him.nhcr.mediator.hl7v2.message");
        context.setModelClassFactory(cmf);

        Parser parser = context.getPipeParser();

        return (ZXT_A01) parser.parse(zxtA01Hl7Message);
    }
}
