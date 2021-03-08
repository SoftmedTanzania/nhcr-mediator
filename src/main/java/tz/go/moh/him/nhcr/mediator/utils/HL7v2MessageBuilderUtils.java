package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.IN1;
import ca.uhn.hl7v2.model.v231.segment.MRG;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientConflictResolutions;
import tz.go.moh.him.nhcr.mediator.domain.ClientId;
import tz.go.moh.him.nhcr.mediator.domain.ClientInsurance;
import tz.go.moh.him.nhcr.mediator.domain.ClientProgram;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A40;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.segment.ZXT;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
     * @param receivingFacility    The receiving facility.
     * @param receivingApplication The receiving Application.
     * @param securityAccessToken  The sending facility security token.
     * @param messageControlId     The number or other identifier that uniquely identifies the message
     * @param recodedDate          The recoded date
     * @param client               The emr client object
     * @return Returns the created ZXT A01 message.
     * @throws IOException  The IOException thrown
     * @throws HL7Exception The HL7Expeption thrown
     */
    public static ZXT_A01 createZxtA01(String messageTriggerEvent, String sendingApplication, String facilityHfrCode, String receivingFacility, String receivingApplication, String securityAccessToken, String messageControlId, Date recodedDate, Client client) throws HL7Exception, IOException {
        ZXT_A01 adt = new ZXT_A01();
        adt.initQuickstart("ADT", messageTriggerEvent, "P");

        //Populating the MSH Segment
        populateMshSegment(adt.getMSH(), sendingApplication, facilityHfrCode, receivingApplication, receivingFacility, recodedDate, securityAccessToken, messageControlId);

        //Populating the EVN Segment
        populateEvnSegment(adt.getEVN(), recodedDate);

        //Populating the PID Segment
        populatePidSegment(adt.getPID(), client);

        //Populating IN1 Segment
        if (client.getInsurance() != null) {
            populateIn1Segment(adt.getIN1IN2IN3().getIN1(), client.getInsurance());
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
        populateZxtSegment(adt.getZXT(), votersId, ritaId);


        return adt;
    }

    /**
     * Creates an ZXT A40 message.
     *
     * @param sendingApplication   The sending Application.
     * @param facilityHfrCode      The facility HFR code.
     * @param receivingFacility    The receiving facility.
     * @param receivingApplication The receiving Application.
     * @param securityAccessToken  The sending facility security token.
     * @param messageControlId     The number or other identifier that uniquely identifies the message
     * @param recodedDate          The recoded date
     * @param client               The emr client object
     * @return Returns the created ZXT A01 message.
     * @throws IOException  The IOException thrown
     * @throws HL7Exception The HL7Expeption thrown
     */
    public static ZXT_A40 createZxtA40(String sendingApplication, String facilityHfrCode, String receivingFacility, String receivingApplication, String securityAccessToken, String messageControlId, Date recodedDate, ClientConflictResolutions client) throws HL7Exception, IOException {
        ZXT_A40 adt = new ZXT_A40();
        adt.initQuickstart("ADT", "A40", "P");

        //Populating the MSH Segment
        populateMshSegment(adt.getMSH(), sendingApplication, facilityHfrCode, receivingApplication, receivingFacility, recodedDate, securityAccessToken, messageControlId);

        //Populating the EVN Segment
        populateEvnSegment(adt.getEVN(), recodedDate);

        //Populating the PID Segment
        populatePidSegment(adt.getPIDPD1MRGPV1().getPID(), client);

        //Populating the MRG Segment
        populateMrgSegment(adt.getPIDPD1MRGPV1().getMRG(), client.getMergedRecords());

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
        populateZxtSegment(adt.getZXT(), votersId, ritaId);


        return adt;
    }


    /**
     * Populates the MSH Segment
     *
     * @param mshSegment           The MSH segment
     * @param sendingApplication   The sending Application.
     * @param sendingFacility      The sending Facility.
     * @param receivingApplication The receiving Application.
     * @param receivingFacility    The receiving Facility.
     * @param dateTimeOfTheMessage The date time of the message.
     * @param security             The security access token
     * @param messageControlId     The message control ID
     * @throws DataTypeException The exception thrown
     */
    private static void populateMshSegment(MSH mshSegment,
                                           String sendingApplication,
                                           String sendingFacility,
                                           String receivingApplication,
                                           String receivingFacility,
                                           Date dateTimeOfTheMessage,
                                           String security,
                                           String messageControlId
    ) throws DataTypeException {
        mshSegment.getSendingApplication().getNamespaceID().setValue(sendingApplication);
        mshSegment.getSendingFacility().getNamespaceID().setValue(sendingFacility);
        mshSegment.getReceivingApplication().getNamespaceID().setValue(receivingApplication);
        mshSegment.getReceivingFacility().getNamespaceID().setValue(receivingFacility);
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(dateTimeOfTheMessage);
        mshSegment.getSecurity().setValue(security);
        mshSegment.getVersionID().getVersionID().setValue("2.3.1");
        mshSegment.getProcessingID().getProcessingID().setValue("P");
        mshSegment.getMessageControlID().setValue(messageControlId);
    }

    /**
     * Populates the EVN Segment
     *
     * @param evnSegment       The EVN segment to be populated
     * @param recordedDateTime The recorded date time
     * @throws DataTypeException The exception thrown
     */
    private static void populateEvnSegment(EVN evnSegment, Date recordedDateTime) throws DataTypeException {
        evnSegment.getRecordedDateTime().getTimeOfAnEvent().setValue(recordedDateTime);
    }

    /**
     * @param pidSegment The PID segment to be populated
     * @param client     The emr client object
     * @throws HL7Exception The exception thrown
     */
    private static void populatePidSegment(PID pidSegment, Client client) throws HL7Exception {
        // Populating the PID.3
        for (int i = 0; i < client.getPrograms().size(); i++) {
            //Populating the client ids.
            ClientProgram clientProgram = client.getPrograms().get(i);
            pidSegment.getPatientIdentifierList(i).getID().setValue(clientProgram.getId());
            pidSegment.getPatientIdentifierList(i).getAssigningAuthority().getNamespaceID().setValue(clientProgram.getAssigningAuthority());
            pidSegment.getPatientIdentifierList(i).getAssigningFacility().getNamespaceID().setValue(clientProgram.getAssigningFacility());
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
    }

    /**
     * Populates the IN1 Segment
     *
     * @param in1Segment The IN1 segment to be populated
     * @param insurance  The client insurance object
     * @throws DataTypeException The exception thrown
     */
    private static void populateIn1Segment(IN1 in1Segment, ClientInsurance insurance) throws DataTypeException {
        in1Segment.getSetIDIN1().setValue("1");
        in1Segment.getInsuredSIDNumber(0).getID().setValue(insurance.getId()); //TODO have a discussion on this
        in1Segment.getInsuranceCompanyName(0).getOrganizationName().setValue(insurance.getName());
    }

    /**
     * Populates the ZXT Segment
     *
     * @param zxtSegment The ZXT segment to be populated
     * @param votesId    The Client's Voters Id
     * @param ritaId     The Client's Rita ID
     * @throws HL7Exception The Exception thrown
     */
    private static void populateZxtSegment(ZXT zxtSegment, String votesId, String ritaId) throws HL7Exception {
        if (votesId != null)
            zxtSegment.getVotersId().setValue(votesId);

        if (ritaId != null) {
            zxtSegment.getRitaId().getId().setValue(ritaId);
            zxtSegment.getRitaId().getIdType().setValue("BTH_CRT");
            zxtSegment.getRitaId().getCountryName().setValue("Tanzania");
            zxtSegment.getRitaId().getCountryCode().setValue("TZA");
        }
    }

    /**
     * Populates the MRG Segment
     *
     * @param mrgSegment The merge segment to be populated
     * @param mergedIds  The NHCR assigned client identifier of the merged conflict
     * @throws DataTypeException The exception thrown if it occurs
     */
    private static void populateMrgSegment(MRG mrgSegment, List<ClientConflictResolutions.CrId> mergedIds) throws DataTypeException {
        int position = 0;
        for (ClientConflictResolutions.CrId crId : mergedIds) {
            mrgSegment.getMrg1_PriorPatientIdentifierList(position).getID().setValue(crId.getCrId());
            mrgSegment.getMrg1_PriorPatientIdentifierList(position).getAssigningAuthority().getNamespaceID().setValue("CR_CID");
            position++;
        }

    }

    /**
     * Encodes the ZXT message into a HL7v2 message string
     *
     * @param message The ZXT message
     * @return The HL7v2 encoded string
     * @throws HL7Exception The exception thrown
     */
    public static String encodeZxtMessage(AbstractMessage message) throws HL7Exception {
        HapiContext context = new DefaultHapiContext();

        //Creating a custom model class factory for the custom ZTX_A01 and ZTX_A40 message
        ModelClassFactory cmf = new CustomModelClassFactory("tz.go.moh.him.nhcr.mediator.hl7v2.message");
        context.setModelClassFactory(cmf);

        Parser parser = context.getPipeParser();

        return parser.encode(message);
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
        Parser parser = context.getPipeParser();

        //Creating a custom model class factory for the custom ZTX_A01 message
        ModelClassFactory cmf = new CustomModelClassFactory("tz.go.moh.him.nhcr.mediator.hl7v2");
        context.setModelClassFactory(cmf);

        //Replacing the message MSH.9 Message type to the custom ZXT Type inorder for parser to correctly parse the message.
        String hl7MessageString = zxtA01Hl7Message.replace("ADT^A01", "ZXT^A01");

        ZXT_A01 zxtA01 = (ZXT_A01) parser.parse(hl7MessageString);

        //Reverting back the message MSH.9 Message type to the original ADT^A01.
        zxtA01.getMSH().getMessageType().getMessageType().setValue("ADT");
        zxtA01.getMSH().getMessageType().getTriggerEvent().setValue("A01");

        return zxtA01;
    }

    /**
     * Parses the HL7v2 message string to a ZXT_A01 message
     *
     * @param zxtA40Hl7Message The HL7v2 encoded string
     * @return The ZXT_A40 Message Object
     * @throws HL7Exception The exception thrown
     */
    public static ZXT_A40 parseZxtA40Message(String zxtA40Hl7Message) throws HL7Exception {
        HapiContext context = new DefaultHapiContext();
        Parser parser = context.getPipeParser();

        //Creating a custom model class factory for the custom ZTX_A40 message
        ModelClassFactory cmf = new CustomModelClassFactory("tz.go.moh.him.nhcr.mediator.hl7v2");
        context.setModelClassFactory(cmf);

        //Replacing the message MSH.9 Message type to the custom ZXT Type inorder for parser to correctly parse the message.
        String hl7MessageString = zxtA40Hl7Message.replace("ADT^A40", "ZXT^A40");

        ZXT_A40 zxtA40 = (ZXT_A40) parser.parse(hl7MessageString);

        //Reverting back the message MSH.9 Message type to the original ADT^A01.
        zxtA40.getMSH().getMessageType().getMessageType().setValue("ADT");
        zxtA40.getMSH().getMessageType().getTriggerEvent().setValue("A40");

        return zxtA40;
    }
}
