package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.message.ADT_A01;
import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import ca.uhn.hl7v2.model.v231.segment.EVN;
import ca.uhn.hl7v2.model.v231.segment.MSH;
import ca.uhn.hl7v2.model.v231.segment.PID;
import tz.go.moh.him.mediator.core.exceptions.ArgumentException;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientProgram;

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
     * Creates an ADT A01 message.
     *
     * @param sendingFacility      The sending facility.
     * @param facilityHfrCode      The facility HFR code.
     * @param facilityOid          The sending facility OID.
     * @param receivingFacility    The receiving facility.
     * @param receivingApplication The receiving Application.
     * @param securityAccessToken  The sending facility security token.
     * @param client               the client registered
     * @return Returns the created ADT A01 message.
     */
    public static ADT_A01 createAdtA01(String sendingFacility, String facilityHfrCode, String facilityOid, String receivingFacility, String receivingApplication, String securityAccessToken, Client client) throws IOException, HL7Exception {
        ADT_A01 adt = new ADT_A01();
        adt.initQuickstart("ADT", "A01", "P");

        // Populate the MSH Segment
        MSH mshSegment = adt.getMSH();
        mshSegment.getSendingApplication().getNamespaceID().setValue(facilityOid);
        mshSegment.getSendingFacility().getNamespaceID().setValue(facilityHfrCode);
        mshSegment.getReceivingApplication().getNamespaceID().setValue(receivingApplication);
        mshSegment.getReceivingFacility().getNamespaceID().setValue(receivingFacility);
        mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(Calendar.getInstance());
        mshSegment.getSecurity().setValue(securityAccessToken);
        mshSegment.getVersionID().getVersionID().setValue("2.3.1");


        // Populate the EVN Segment
        EVN evnSegment = adt.getEVN();
        evnSegment.getRecordedDateTime().getTimeOfAnEvent().setValue(Calendar.getInstance());


        // Populate the PID Segment
        PID pid = adt.getPID();

        // Populate the PID.3
        for (int i = 0; i < client.getPrograms().size(); i++) {
            ClientProgram clientProgram = client.getPrograms().get(i);
            pid.getPatientIdentifierList(i).getID().setValue(clientProgram.getId());
            pid.getPatientIdentifierList(i).getAssigningAuthority().getNamespaceID().setValue(clientProgram.getName());
            pid.getPatientIdentifierList(i).getAssigningFacility().getNamespaceID().setValue(facilityHfrCode);
        }
        pid.getPatientName(0).getFamilyLastName().getFamilyName().setValue(client.getLastName());
        pid.getPatientName(0).getGivenName().setValue(client.getFirstName());
        pid.getPatientName(0).getMiddleInitialOrName().setValue(client.getMiddleName());
        pid.getPatientName(0).getNameTypeCode().setValue("L");

        if (client.getSex().equalsIgnoreCase("male")) {
            pid.getSex().setValue("M");
        } else if (client.getSex().equalsIgnoreCase("female")) {
            pid.getSex().setValue("F");
        } else {
            throw new ArgumentException();
        }


        return new ADT_A01();
    }
}
