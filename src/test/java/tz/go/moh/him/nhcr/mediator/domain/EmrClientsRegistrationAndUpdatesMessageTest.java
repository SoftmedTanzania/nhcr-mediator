package tz.go.moh.him.nhcr.mediator.domain;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateDeserializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Contains tests for the {@link EmrClientsRegistrationAndUpdatesMessage} class.
 */
public class EmrClientsRegistrationAndUpdatesMessageTest {
    /**
     * Tests the serialization of an EMR Client Registration and Updates message.
     */
    @Test
    public void testEmrClientRegistrationAndUpdatesMessageSerialization() {
        InputStream stream = EmrClientsRegistrationAndUpdatesMessageTest.class.getClassLoader().getResourceAsStream("register_client.json");

        Assert.assertNotNull(stream);

        String data;

        try {
            data = IOUtils.toString(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertNotNull(data);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate.class, new AttributePostOrUpdateDeserializer());
        Gson gson = gsonBuilder.create();

        EmrClientsRegistrationAndUpdatesMessage emrClientsRegistrationAndUpdatesMessage = gson.fromJson(data, EmrClientsRegistrationAndUpdatesMessage.class);

        Assert.assertEquals("Mirembe MHH", emrClientsRegistrationAndUpdatesMessage.getSendingFacility());
        Assert.assertEquals("Blaj9747", emrClientsRegistrationAndUpdatesMessage.getFacilityHfrCode());
        Assert.assertEquals("AFYA CARE", emrClientsRegistrationAndUpdatesMessage.getOid());
        Assert.assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis", emrClientsRegistrationAndUpdatesMessage.getToken());

        EmrClientsRegistrationAndUpdatesMessage.Client client = emrClientsRegistrationAndUpdatesMessage.getClients().get(0);
        Assert.assertEquals("52c9fa36-6b7f-483f-8c4a-1ad033e78618", client.getMrn());
        Assert.assertEquals("P", client.getPostOrUpdate().getValue());
        Assert.assertEquals("fname", client.getFirstName());
        Assert.assertEquals("mname", client.getMiddleName());
        Assert.assertEquals("lname", client.getLastName());
        Assert.assertEquals("oname", client.getOtherName());
        Assert.assertEquals("981426-6090", client.getUln());

        Assert.assertEquals("12345", client.getIds().get(0).getId());
        Assert.assertEquals("NATIONAL_ID", client.getIds().get(0).getType());

        Assert.assertEquals("12345", client.getPrograms().get(0).getId());
        Assert.assertEquals("CTC", client.getPrograms().get(0).getName());

        Assert.assertEquals("12345", client.getInsurance().getId());
        Assert.assertEquals("NHIF", client.getInsurance().getName());

        Assert.assertEquals("FEMALE", client.getSex());
        Assert.assertEquals("2020-03-15", client.getDob());
        Assert.assertEquals("Manyara", client.getPermanentAddress().getRegion());
        Assert.assertEquals("Kiteto", client.getPermanentAddress().getCouncil());
        Assert.assertEquals("Ayasanda", client.getPermanentAddress().getWard());
        Assert.assertEquals("Robayambao", client.getPermanentAddress().getVillage());
        Assert.assertEquals("255", client.getCountryCode());
        Assert.assertEquals("0754886287", client.getPhoneNumber());

        Assert.assertEquals("12232131", client.getFamilyLinkages().getId());
        Assert.assertEquals("NATIONAL_ID", client.getFamilyLinkages().getSourceOfId());
        Assert.assertEquals("Parent", client.getFamilyLinkages().getTypeOfLinkage());

        Assert.assertEquals("101335-8", client.getPlaceEncountered());
        Assert.assertEquals("0", client.getStatus());
        Assert.assertEquals("2020-06-02T07:07:20.000Z", client.getCreatedAt());

    }

}