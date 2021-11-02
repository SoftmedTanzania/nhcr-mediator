package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v231.datatype.XAD;
import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import ca.uhn.hl7v2.model.v231.message.QRY_A19;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.IN1;
import ca.uhn.hl7v2.model.v231.segment.MRG;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import ca.uhn.hl7v2.model.v231.segment.QRD;
import ca.uhn.hl7v2.model.v231.segment.QRF;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;
import org.codehaus.plexus.util.StringUtils;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.nhcr.mediator.domain.*;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.group.ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZDR_A19;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A40;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.segment.ZXT;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Represents an HL7v2 message builder utility.
 */
public class HL7v2MessageBuilderUtils {
    /**
     * The national id.
     */
    public static final String NATIONAL_ID = "NATIONAL_ID";
    /**
     * The ULN.
     */
    public static final String ULN = "ULN";
    /**
     * The voters id.
     */
    public static final String VOTERS_ID = "VOTERS_ID";
    /**
     * The drivers license id.
     */
    private static final String DRIVERS_LICENSE_ID = "DRIVERS_LICENSE_ID";
    /**
     * The Rita id.
     */
    private static final String RITA_ID = "RITA_ID";
    /**
     * The EMR date format.
     */
    private static SimpleDateFormat eventDateTimeDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * The NHCR date format.
     */
    private static SimpleDateFormat nhcrDateFormat = new SimpleDateFormat("yyyyMMdd");
    /**
     * The EMR date format.
     */
    private static SimpleDateFormat emrDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates an ADT A04 message.
     *
     * @return Returns the created ADT A04 message.
     */
    public static ADT_A04 createAdtA04() {
        return new ADT_A04();
    }

    /**
     * Creates an QRY A19 message.
     *
     * @param sendingApplication    The sending Application.
     * @param facilityHfrCode       The facility HFR code.
     * @param receivingFacility     The receiving facility.
     * @param receivingApplication  The receiving Application.
     * @param securityAccessToken   The sending facility security token.
     * @param messageControlId      The number or other identifier that uniquely identifies the message
     * @param queryDateTime         The query date
     * @param id                    The client id
     * @param idType                The client id type
     * @param whatQualifier         The What User Qualifier
     * @param startDateTime         The When Data start date/time
     * @param endDateTime           The When Data end date/time
     * @param offset                The Offset
     * @param limit                 The Limit
     * @param otherQrySubjectFilter The other query subject filter
     * @return Returns the created QRY A19 message.
     * @throws IOException  The IOException thrown
     * @throws HL7Exception The HL7Expeption thrown
     */
    public static QRY_A19 createQryA19(String sendingApplication, String facilityHfrCode, String receivingFacility, String receivingApplication, String securityAccessToken, String messageControlId, Date queryDateTime, String id, String idType, String whatQualifier, String startDateTime, String endDateTime, String offset, String limit, String otherQrySubjectFilter) throws HL7Exception, IOException {
        QRY_A19 qry = new QRY_A19();
        qry.initQuickstart("QRY", "A19", "P");

        // Populating the MSH Segment
        populateMshSegment(qry.getMSH(), "QRY_A19", sendingApplication, facilityHfrCode, receivingApplication, receivingFacility, queryDateTime, securityAccessToken, messageControlId);

        // Populating the QRD Segment
        populateQrdSegment(qry.getQRD(), queryDateTime, id, idType, offset, limit);

        // Populating the QRF Segment
        populateQrfSegment(qry.getQRF(), whatQualifier, startDateTime, endDateTime, otherQrySubjectFilter);

        return qry;
    }

    /**
     * Creates an ZXT A01 message.
     *
     * @param messageTriggerEvent  The MSH message trigger event.
     * @param messageStructure     The MSH message structure.
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
    public static ZXT_A01 createZxtA01(String messageTriggerEvent, String messageStructure, String sendingApplication, String facilityHfrCode, String receivingFacility, String receivingApplication, String securityAccessToken, String messageControlId, Date recodedDate, Client client) throws HL7Exception, IOException {
        ZXT_A01 adt = new ZXT_A01();
        adt.initQuickstart("ADT", messageTriggerEvent, "P");

        //Populating the MSH Segment
        populateMshSegment(adt.getMSH(), messageStructure, sendingApplication, facilityHfrCode, receivingApplication, receivingFacility, recodedDate, securityAccessToken, messageControlId);

        //Populating the EVN Segment
        populateEvnSegment(adt.getEVN(), recodedDate);

        //Populating the PID Segment
        populatePidSegment(adt.getPID(), client, facilityHfrCode, sendingApplication);

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
        populateMshSegment(adt.getMSH(), "ADT_A40", sendingApplication, facilityHfrCode, receivingApplication, receivingFacility, recodedDate, securityAccessToken, messageControlId);

        //Populating the EVN Segment
        populateEvnSegment(adt.getEVN(), recodedDate);

        //Populating the PID Segment
        populatePidSegment(adt.getPIDPD1MRGPV1().getPID(), client, facilityHfrCode, sendingApplication);

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
     * @param messageStructure     The MSH message structure
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
                                           String messageStructure,
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
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(eventDateTimeDateFormat.format(dateTimeOfTheMessage));
        mshSegment.getSecurity().setValue(security);
        mshSegment.getMessageType().getMessageStructure().setValue(messageStructure);
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
        evnSegment.getRecordedDateTime().getTimeOfAnEvent().setValue(nhcrDateFormat.format(recordedDateTime));
    }

    /**
     * @param pidSegment The PID segment to be populated
     * @param client     The emr client object
     * @throws HL7Exception The exception thrown
     */
    private static void populatePidSegment(PID pidSegment, Client client, String facilityHfrCode, String sendingApplication) throws HL7Exception {
        // Populating the PID.3
        for (int i = 0; i < client.getPrograms().size(); i++) {
            //Populating the client ids.
            ClientProgram clientProgram = client.getPrograms().get(i);
            pidSegment.getPatientIdentifierList(i).getID().setValue(clientProgram.getId());
            pidSegment.getPatientIdentifierList(i).getAssigningAuthority().getNamespaceID().setValue(clientProgram.getAssigningAuthority());
            pidSegment.getPatientIdentifierList(i).getAssigningFacility().getNamespaceID().setValue(clientProgram.getAssigningFacility());
        }

        // MRN
        int ids = pidSegment.getPatientIdentifierListReps();
        pidSegment.getPatientIdentifierList(ids).getID().setValue(client.getMrn());
        pidSegment.getPatientIdentifierList(ids).getAssigningAuthority().getNamespaceID().setValue(sendingApplication);
        pidSegment.getPatientIdentifierList(ids).getAssigningFacility().getNamespaceID().setValue(client.getPlaceEncountered());

        //Populating the client names.
        pidSegment.getPatientName(0).getFamilyLastName().getFamilyName().setValue(client.getLastName());
        pidSegment.getPatientName(0).getGivenName().setValue(client.getFirstName());
        pidSegment.getPatientName(0).getMiddleInitialOrName().setValue(client.getMiddleName());
        pidSegment.getPatientName(0).getNameTypeCode().setValue("L");

        //Populating the client date of birth.
        Date dob = DateUtils.checkDateFormatStrings(client.getDob());
        pidSegment.getDateTimeOfBirth().getTimeOfAnEvent().setValue(nhcrDateFormat.format(dob));

        //Populating the client death status.
        pidSegment.getPatientDeathIndicator().setValue(client.isDeathStatus() ? "Y" : "N");

        if (client.isDeathStatus()) {
            //Populating the client death date.
            Date deathDate = DateUtils.checkDateFormatStrings(client.getDeathDate());
            pidSegment.getPatientDeathDateAndTime().getTimeOfAnEvent().setValue(nhcrDateFormat.format(deathDate));
        }

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
            String councilWardVillage = "";
            if (client.getResidentialAddress().getCouncil() != null) {
                councilWardVillage = client.getResidentialAddress().getCouncil();
            }

            if (client.getResidentialAddress().getWard() != null) {
                councilWardVillage = councilWardVillage + "*" + client.getResidentialAddress().getWard();
            } else {
                councilWardVillage = councilWardVillage + "*";
            }

            if (client.getResidentialAddress().getVillage() != null) {
                councilWardVillage = councilWardVillage + "*" + client.getResidentialAddress().getVillage();
            } else {
                councilWardVillage = councilWardVillage + "*";
            }
            pidSegment.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pidSegment.getPatientAddress(patientAddressIndex).getCity().setValue(client.getResidentialAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getResidentialAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getAddressType().setValue("C");
            patientAddressIndex++;
        }

        if (client.getPermanentAddress() != null) {
            //Populating Permanent Address
            String councilWardVillage = "";
            if (client.getPermanentAddress().getCouncil() != null) {
                councilWardVillage = client.getPermanentAddress().getCouncil();
            }

            if (client.getPermanentAddress().getWard() != null) {
                councilWardVillage = councilWardVillage + "*" + client.getPermanentAddress().getWard();
            } else {
                councilWardVillage = councilWardVillage + "*";
            }

            if (client.getPermanentAddress().getVillage() != null) {
                councilWardVillage = councilWardVillage + "*" + client.getPermanentAddress().getVillage();
            } else {
                councilWardVillage = councilWardVillage + "*";
            }
            pidSegment.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pidSegment.getPatientAddress(patientAddressIndex).getCity().setValue(client.getPermanentAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getPermanentAddress().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getAddressType().setValue("H");
            patientAddressIndex++;
        }

        if (client.getPlaceOfBirth() != null) {
            //Populating Place of birth
            String councilWardVillage = "";
            if (client.getPlaceOfBirth().getCouncil() != null) {
                councilWardVillage = client.getPlaceOfBirth().getCouncil();
            }

            if (client.getPlaceOfBirth().getWard() != null) {
                councilWardVillage = councilWardVillage + "*" + client.getPlaceOfBirth().getWard();
            } else {
                councilWardVillage = councilWardVillage + "*";
            }

            if (client.getPlaceOfBirth().getVillage() != null) {
                councilWardVillage = councilWardVillage + "*" + client.getPlaceOfBirth().getVillage();
            } else {
                councilWardVillage = councilWardVillage + "*";
            }

            pidSegment.getPatientAddress(patientAddressIndex).getOtherDesignation().setValue(councilWardVillage);
            pidSegment.getPatientAddress(patientAddressIndex).getCity().setValue(client.getPlaceOfBirth().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getStateOrProvince().setValue(client.getPlaceOfBirth().getRegion());
            pidSegment.getPatientAddress(patientAddressIndex).getAddressType().setValue("BR");
            patientAddressIndex++;
        }

        //Populating phone number
        pidSegment.getPhoneNumberHome(0).getTelecommunicationUseCode().setValue("PRN");
        pidSegment.getPhoneNumberHome(0).getTelecommunicationEquipmentType().setValue("PH");
        pidSegment.getPhoneNumberHome(0).getCountryCode().setValue(client.getPhoneNumber().getPrefix());
        pidSegment.getPhoneNumberHome(0).getPhoneNumber().setValue(client.getPhoneNumber().getNumber());

        for (ClientId clientId : client.getIds()) {
            if (clientId.getType().equalsIgnoreCase("DRIVERS_LICENSE_ID")) {
                //Populating drivers license
                pidSegment.getDriverSLicenseNumberPatient().getDriverSLicenseNumber().setValue(clientId.getId());
            } else if (clientId.getType().equalsIgnoreCase("NATIONAL_ID")) {
                //Populating national ID
                pidSegment.getNationality().getIdentifier().setValue(clientId.getId());
            } else if (clientId.getType().equalsIgnoreCase("ULN")) {
                //Populating uln
                pidSegment.getSSNNumberPatient().setValue(clientId.getId());
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
        String hl7MessageString = zxtA01Hl7Message.replace("ADT^A01^ADT_A01", "ZXT^A01^ZXT_A01");

        ZXT_A01 zxtA01 = (ZXT_A01) parser.parse(hl7MessageString);

        //Reverting back the message MSH.9 Message type to the original ADT^A01.
        zxtA01.getMSH().getMessageType().getMessageType().setValue("ADT");
        zxtA01.getMSH().getMessageType().getTriggerEvent().setValue("A01");
        zxtA01.getMSH().getMessageType().getMessageStructure().setValue("ADT_A01");

        return zxtA01;
    }

    /**
     * Parses the HL7v2 message string to a ZXT_A40 message
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
        String hl7MessageString = zxtA40Hl7Message.replace("ADT^A40^ADT_A40", "ZXT^A40^ZXT_A40");

        ZXT_A40 zxtA40 = (ZXT_A40) parser.parse(hl7MessageString);

        //Reverting back the message MSH.9 Message type to the original ADT^A01.
        zxtA40.getMSH().getMessageType().getMessageType().setValue("ADT");
        zxtA40.getMSH().getMessageType().getTriggerEvent().setValue("A40");
        zxtA40.getMSH().getMessageType().getMessageStructure().setValue("ADT_A40");

        return zxtA40;
    }

    /**
     * Populates the QRD Segment
     *
     * @param qrdSegment    The QRD segment to be populated
     * @param queryDateTime The query date time
     * @param id            The client id
     * @param idType        The client id type
     * @param offset        The Offset
     * @param limit         The Limit
     * @throws DataTypeException The exception thrown
     */
    private static void populateQrdSegment(QRD qrdSegment, Date queryDateTime, String id, String idType, String offset, String limit) throws DataTypeException {
        qrdSegment.getQueryDateTime().getTimeOfAnEvent().setValue(nhcrDateFormat.format(queryDateTime));
        qrdSegment.getQueryFormatCode().setValue("R");
        qrdSegment.getQueryPriority().setValue("I");
        if (!id.isEmpty() && !idType.isEmpty()) {
            qrdSegment.getWhatSubjectFilter(0).getIdentifier().setValue(id);
            qrdSegment.getWhatSubjectFilter(0).getText().setValue(idType);
        }

        // Set the offset and limit
        if (offset != null && !offset.isEmpty()) {
            qrdSegment.getQueryResultsLevel().setValue(offset);
        }

        if (limit != null && !limit.isEmpty()) {
            qrdSegment.getQuantityLimitedRequest().getQuantity().setValue(limit);
        }
    }

    /**
     * Populates the QRF Segment
     *
     * @param qrfSegment            The QRF segment to be populated
     * @param whatQualifier         The What User Qualifier value
     * @param startDateTime         The When Data start date/time
     * @param endDateTime           The When Data end date/time
     * @param otherQrySubjectFilter The other query subject filter
     * @throws DataTypeException The exception thrown
     */
    private static void populateQrfSegment(QRF qrfSegment, String whatQualifier, String startDateTime, String endDateTime, String otherQrySubjectFilter) throws DataTypeException {
        qrfSegment.getWhatUserQualifier(0).setValue(whatQualifier);
        if (whatQualifier.equalsIgnoreCase("CONFLICTS")) {

            if (!StringUtils.isBlank(startDateTime) && !StringUtils.isBlank(endDateTime)) {
                // normalize the incoming date time format
                qrfSegment.getWhenDataStartDateTime().getTimeOfAnEvent().setValue(startDateTime);
                qrfSegment.getWhenDataEndDateTime().getTimeOfAnEvent().setValue(endDateTime);
            }

            if (!StringUtils.isBlank(otherQrySubjectFilter)) {
                qrfSegment.getOtherQRYSubjectFilter(0).setValue(otherQrySubjectFilter);
            }
        }
    }

    /**
     * Parses the HL7v2 ADR_A19 message and returned found clients
     *
     * @param adrA19Hl7Message The HL7v2 ADR_A19 encoded string
     * @return The A list of clients
     * @throws HL7Exception The exception thrown
     */
    public static List<Client> parseAdrA19Message(String adrA19Hl7Message) throws HL7Exception {
        List<Client> retVal = new ArrayList<>();

        HapiContext context = new DefaultHapiContext();
        Parser parser = context.getPipeParser();

        // Creating a custom model class factory for the custom ZDR_A19 message
        ModelClassFactory cmf = new CustomModelClassFactory("tz.go.moh.him.nhcr.mediator.hl7v2");
        context.setModelClassFactory(cmf);

        //Replacing the message MSH.9 Message type to the custom ZXT Type inorder for parser to correctly parse the message.
        adrA19Hl7Message = adrA19Hl7Message.replace("ADR^A19^ADR_A19", "ZDR^A19^ZDR_A19");
        adrA19Hl7Message = adrA19Hl7Message.replace("ADR^A19", "ZDR^A19");

        // fix MSH-12
        adrA19Hl7Message = adrA19Hl7Message.replace("|2.5", "|2.3.1");

        ZDR_A19 zdr = (ZDR_A19) parser.parse(adrA19Hl7Message);

        // Get MSH-5 & MSH-6
        String assigningAuthority = ((MSH) zdr.get("MSH")).getReceivingApplication().getNamespaceID().getValue();
        String facilityCode = ((MSH) zdr.get("MSH")).getReceivingFacility().getNamespaceID().getValue();

        List<ZDR_A19_EVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXT> groups = zdr.getEVNPIDPD1NK1PV1PV2DB1OBXAL1DG1DRGPR1ROLGT1IN1IN2IN3ACCUB1UB2ZXTAll();
        for (int i = 0; i < groups.size(); i++) {
            Client client = new Client();
            PID pid = groups.get(i).getPID();

            if (pid == null) continue;

            // Identifiers
            int ids = pid.getPatientIdentifierListReps();
            if (ids > 0) {
                for (int j = 0; j < ids; j++) {

                    // skip logic for mrn
                    if (
                            assigningAuthority.equalsIgnoreCase(pid.getPatientIdentifierList(j).getAssigningAuthority().getNamespaceID().getValueOrEmpty()) ||
                                    facilityCode.equalsIgnoreCase(pid.getPatientIdentifierList(j).getAssigningFacility().getNamespaceID().getValueOrEmpty())
                    ) {
                        // Set the mrn on the client
                        client.setMrn(pid.getPatientIdentifierList(j).getID().getValue());

                        // Set the encounter location
                        client.setPlaceEncountered(pid.getPatientIdentifierList(j).getAssigningFacility().getNamespaceID().getValue());

                        continue;
                    }

                    // skip logic for CR_CID
                    if (pid.getPatientIdentifierList(j).getAssigningAuthority().getNamespaceID().getValueOrEmpty().equalsIgnoreCase("CR_CID")) {
                        client.getIds().add(new ClientId("CR_CID", pid.getPatientIdentifierList(j).getID().getValue()));

                        continue;
                    }

                    // skip logic for ENT_ID
                    if (pid.getPatientIdentifierList(j).getAssigningAuthority().getNamespaceID().getValueOrEmpty().equalsIgnoreCase("ENT_ID")) {
                        client.getIds().add(new ClientId("ENT_ID", pid.getPatientIdentifierList(j).getID().getValue()));

                        continue;
                    }

                    ClientProgram program = new ClientProgram();
                    program.setId(pid.getPatientIdentifierList(j).getID().getValue());
                    program.setAssigningAuthority(pid.getPatientIdentifierList(j).getAssigningAuthority().getNamespaceID().getValue());
                    program.setAssigningFacility(pid.getPatientIdentifierList(j).getAssigningFacility().getNamespaceID().getValue());

                    client.getPrograms().add(program);
                }
            }

            // Name
            if (pid.getPatientName(0) != null) {
                client.setLastName(pid.getPatientName(0).getFamilyLastName().getFamilyName().getValue());
                client.setFirstName(pid.getPatientName(0).getGivenName().getValue());
                client.setMiddleName(pid.getPatientName(0).getMiddleInitialOrName().getValue());
            }

            // Other name
            if (pid.getPatientAlias(0) != null) {
                client.setOtherName(pid.getPatientAlias(0).getGivenName().getValue());
            }

            // Sex
            if (!pid.getSex().isEmpty()) {
                if (pid.getSex().getValueOrEmpty().equalsIgnoreCase("M")) {
                    client.setSex("MALE");
                } else if (pid.getSex().getValueOrEmpty().equalsIgnoreCase("F")) {
                    client.setSex("FEMALE");
                }
            }

            // Address
            XAD permanentAddress = Arrays.stream(pid.getPatientAddress()).filter(c -> "H".equals(c.getAddressType().getValue())).findFirst().orElse(null);
            XAD residentialAddress = Arrays.stream(pid.getPatientAddress()).filter(c -> "C".equals(c.getAddressType().getValue())).findFirst().orElse(null);
            XAD birthAddress = Arrays.stream(pid.getPatientAddress()).filter(c -> "BR".equals(c.getAddressType().getValue())).findFirst().orElse(null);

            if (permanentAddress != null) {
                client.setPermanentAddress(mapAddress(permanentAddress));
            }

            if (residentialAddress != null) {
                client.setResidentialAddress(mapAddress(residentialAddress));
            }

            if (birthAddress != null) {
                client.setPlaceOfBirth(mapAddress(birthAddress));
            }

            // Date of Birth
            if (pid.getDateTimeOfBirth() != null && !pid.getDateTimeOfBirth().getTimeOfAnEvent().isEmpty()) {
                try {
                    client.setDob(emrDateFormat.format(nhcrDateFormat.parse(pid.getDateTimeOfBirth().getTimeOfAnEvent().getValue())));
                } catch (ParseException e) {
                    throw new HL7Exception("Unable to parse the date of birth");
                }
            }

            //Set Client Death Status
            client.setDeathStatus(pid.getPatientDeathIndicator().getValue().equalsIgnoreCase("Y"));

            // Set Death Date
            if (pid.getPatientDeathDateAndTime() != null && !pid.getPatientDeathDateAndTime().getTimeOfAnEvent().isEmpty()) {
                try {
                    client.setDeathDate(emrDateFormat.format(nhcrDateFormat.parse(pid.getPatientDeathDateAndTime().getTimeOfAnEvent().getValue())));
                } catch (ParseException e) {
                    throw new HL7Exception("Unable to parse the death date");
                }
            }

            // Set phone number and country code
            if (!pid.getPhoneNumberHome(0).isEmpty()) {
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setPrefix(pid.getPhoneNumberHome(0).getCountryCode().getValue());
                phoneNumber.setNumber(pid.getPhoneNumberHome(0).getPhoneNumber().getValue());

                client.setPhoneNumber(phoneNumber);
            }

            // Set the ULN
            if (!pid.getSSNNumberPatient().isEmpty()) {
                client.getIds().add(new ClientId(ULN, pid.getSSNNumberPatient().getValue()));
            }

            // Set the other name
            if (pid.getPatientAlias(0) != null) {
                client.setOtherName(pid.getPatientAlias(0).getGivenName().getValue());
            }

            // Set the National ID
            if (!pid.getNationality().isEmpty() && !pid.getNationality().getIdentifier().isEmpty()) {
                client.getIds().add(new ClientId(NATIONAL_ID, pid.getNationality().getIdentifier().getValue()));
            }

            // Set the drivers license id
            if (pid.getDriverSLicenseNumberPatient() != null && !pid.getDriverSLicenseNumberPatient().getDriverSLicenseNumber().isEmpty()) {
                client.getIds().add(new ClientId(DRIVERS_LICENSE_ID, pid.getDriverSLicenseNumberPatient().getDriverSLicenseNumber().getValue()));
            }

            // Set the Insurance ID
            IN1 in1 = groups.get(i).getIN1();
            if (in1 != null && !in1.getInsurancePlanID().isEmpty()) {
                ClientInsurance insurance = new ClientInsurance();
                insurance.setId(in1.getInsurancePlanID().getIdentifier().getValue());

                if (in1.getInsuranceCompanyName(0) != null) {
                    insurance.setName(in1.getInsuranceCompanyName(0).getOrganizationName().getValue());
                }

                client.setInsurance(insurance);
            }

            // ZXT - custom identifiers
            ZXT zxt = groups.get(i).getZXT();
            if (zxt != null) {
                // set the voters id
                if (zxt != null && !zxt.getVotersId().isEmpty()) {
                    client.getIds().add(new ClientId(VOTERS_ID, zxt.getVotersId().getValue()));
                }

                // set the rita id
                if (zxt != null && !zxt.getRitaId().getId().isEmpty()) {
                    client.getIds().add(new ClientId(RITA_ID, zxt.getRitaId().getId().getValue()));
                }
            }

            retVal.add(client);
        }

        return retVal;
    }

    /**
     * Maps an address.
     *
     * @param xad The address to map.
     * @return Returns the mapped address.
     */
    private static ClientAddress mapAddress(XAD xad) {
        ClientAddress address = new ClientAddress();

        address.setRegion(xad.getCity().getValue());

        // parse the other designation parts
        if (xad.getOtherDesignation().getValue() != null) {
            String[] designation = xad.getOtherDesignation().getValue().split("\\*");
            if (designation.length == 3) {
                address.setCouncil(designation[0]);
                address.setWard(designation[1]);
                address.setVillage(designation[2]);
            } else if (designation.length == 2) {
                address.setCouncil(designation[0]);
                address.setWard(designation[1]);
            } else if (designation.length == 1) {
                address.setCouncil(designation[0]);
            }
        }

        return address;
    }
}
