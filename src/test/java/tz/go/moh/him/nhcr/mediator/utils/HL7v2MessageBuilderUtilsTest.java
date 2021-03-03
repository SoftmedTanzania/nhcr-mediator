package tz.go.moh.him.nhcr.mediator.utils;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.v231.message.ADT_A04;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tz.go.moh.him.nhcr.mediator.domain.Client;
import tz.go.moh.him.nhcr.mediator.domain.ClientsRegistrationAndUpdatesMessageTest;
import tz.go.moh.him.nhcr.mediator.domain.EmrClientsRegistrationAndUpdatesMessage;
import tz.go.moh.him.nhcr.mediator.hl7v2.v231.message.ZXT_A01;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateDeserializer;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Contains tests for the {@link HL7v2MessageBuilderUtils} class.
 */
public class HL7v2MessageBuilderUtilsTest {
    /**
     * The Gson object.
     */
    private Gson gson;

    /**
     * Runs initialization before each class execution.
     */
    @Before
    public void beforeClass() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Client.PostOrUpdate.class, new AttributePostOrUpdateSerializer());
        gsonBuilder.registerTypeAdapter(Client.PostOrUpdate.class, new AttributePostOrUpdateDeserializer());
        gson = gsonBuilder.create();
    }

    /**
     * Tests the message builder.
     */
    @Test
    public void testCreateAdtA04() {
        ADT_A04 actual = HL7v2MessageBuilderUtils.createAdtA04();

        Assert.assertEquals(ADT_A04.class, actual.getClass());
    }

    /**
     * Tests creating of ZXT_A01.
     */
    @Test
    public void testCreateZxtA01() throws IOException, HL7Exception {
        InputStream registerClientJsonPayloadStream = ClientsRegistrationAndUpdatesMessageTest.class.getClassLoader().getResourceAsStream("register_client.json");

        Assert.assertNotNull(registerClientJsonPayloadStream);
        String registerClientJsonPayload = IOUtils.toString(registerClientJsonPayloadStream);

        EmrClientsRegistrationAndUpdatesMessage message = gson.fromJson(registerClientJsonPayload, EmrClientsRegistrationAndUpdatesMessage.class);


        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        Date recordedDate = new Date(2021, Calendar.FEBRUARY, 25, 0, 0);



        ZXT_A01 zxtA01 = HL7v2MessageBuilderUtils.createZxtA01(
                "A01",
                message.getSendingApplication(),
                message.getFacilityHfrCode(),
                message.getOid(),
                "NHCR",
                "NHCR",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis",
                "1",
                recordedDate,
                message.getClients().get(0)
        );


        InputStream streamExpectedHl7Message = ClientsRegistrationAndUpdatesMessageTest.class.getClassLoader().getResourceAsStream("hl7v2_registration_message");
        Assert.assertNotNull(streamExpectedHl7Message);

        String expectedHl7Message = IOUtils.toString(streamExpectedHl7Message).replaceAll("\\n", "\r");
        Assert.assertEquals(expectedHl7Message, HL7v2MessageBuilderUtils.encodeZxtA01Message(zxtA01));
    }

    /**
     * Tests parsing ZxtA01 Message.
     */
    @Test
    public void testParsingAndEncodingZxtA01Message() throws IOException, HL7Exception {
        InputStream streamHl7Message = ClientsRegistrationAndUpdatesMessageTest.class.getClassLoader().getResourceAsStream("hl7v2_registration_message");

        Assert.assertNotNull(streamHl7Message);

        String expectedHl7MessageString = IOUtils.toString(streamHl7Message).replaceAll("\\n", "\r");

        ZXT_A01 zxtA01 = HL7v2MessageBuilderUtils.parseZxtA01Message(expectedHl7MessageString);

        Assert.assertNotNull(zxtA01);

        String encodedMessageString = HL7v2MessageBuilderUtils.encodeZxtA01Message(zxtA01);

        Assert.assertEquals(expectedHl7MessageString, encodedMessageString);
    }
}