package tz.go.moh.him.nhcr.mediator.domain;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateDeserializer;
import tz.go.moh.him.nhcr.mediator.utils.gsonTypeAdapter.AttributePostOrUpdateSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Contains tests for the {@link EmrClientsRegistrationAndUpdatesMessage} class.
 */
public class EmrClientsRegistrationAndUpdatesMessageTest {
    /**
     * Tests the deserialization of an EMR Client Registration and Updates message.
     */
    @Test
    public void testEmrClientRegistrationAndUpdatesMessageDeserialization() {
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

    /**
     * Tests the serialization of an EMR Client Registration and Updates message.
     */
    @Test
    public void testEmrClientRegistrationAndUpdatesMessageSerialization() {
        EmrClientsRegistrationAndUpdatesMessage emrClientsRegistrationAndUpdatesMessage = new EmrClientsRegistrationAndUpdatesMessage();

        emrClientsRegistrationAndUpdatesMessage.setSendingFacility("Mirembe MHH");
        emrClientsRegistrationAndUpdatesMessage.setFacilityHfrCode("Blaj9747");
        emrClientsRegistrationAndUpdatesMessage.setOid("AFYA CARE");
        emrClientsRegistrationAndUpdatesMessage.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis");

        EmrClientsRegistrationAndUpdatesMessage.Client client = new EmrClientsRegistrationAndUpdatesMessage.Client();
        client.setMrn("52c9fa36-6b7f-483f-8c4a-1ad033e78618");

        client.setPostOrUpdate(EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate.POST);
        client.setFirstName("fname");
        client.setMiddleName("mname");
        client.setLastName("lname");
        client.setOtherName("oname");
        client.setUln("981426-6090");

        EmrClientsRegistrationAndUpdatesMessage.Id nationalId = new EmrClientsRegistrationAndUpdatesMessage.Id();
        nationalId.setId("12345");
        nationalId.setType("NATIONAL_ID");
        client.setIds(Arrays.asList(nationalId));

        EmrClientsRegistrationAndUpdatesMessage.Program ctcProgram = new EmrClientsRegistrationAndUpdatesMessage.Program();
        ctcProgram.setId("12345");
        ctcProgram.setName("CTC");
        client.setPrograms(Arrays.asList(ctcProgram));

        EmrClientsRegistrationAndUpdatesMessage.Insurance insurance = new EmrClientsRegistrationAndUpdatesMessage.Insurance();
        insurance.setId("12345");
        insurance.setName("NHIF");
        client.setInsurance(insurance);
        client.setSex("FEMALE");
        client.setDob("2020-03-15");

        EmrClientsRegistrationAndUpdatesMessage.Address address = new EmrClientsRegistrationAndUpdatesMessage.Address();
        address.setRegion("Manyara");
        address.setCouncil("Kiteto");
        address.setWard("Ayasanda");
        address.setVillage("Robayambao");

        client.setPermanentAddress(address);
        client.setResidentialAddress(address);
        client.setPlaceOfBirth(address);

        EmrClientsRegistrationAndUpdatesMessage.Linkage linkage = new EmrClientsRegistrationAndUpdatesMessage.Linkage();
        linkage.setId("12232131");
        linkage.setSourceOfId("NATIONAL_ID");
        linkage.setTypeOfLinkage("Parent");
        client.setFamilyLinkages(linkage);

        client.setCountryCode("255");
        client.setPhoneNumber("0754886287");
        client.setPlaceEncountered("101335-8");
        client.setStatus("0");
        client.setCreatedAt("2020-06-02T07:07:20.000Z");
        emrClientsRegistrationAndUpdatesMessage.setClients(Arrays.asList(client));





        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(EmrClientsRegistrationAndUpdatesMessage.PostOrUpdate.class, new AttributePostOrUpdateSerializer());
        Gson gson = gsonBuilder.create();

        //Serializing the object into a json
        String json = gson.toJson(emrClientsRegistrationAndUpdatesMessage);

        Assert.assertTrue(json.contains("Mirembe MHH"));
        Assert.assertTrue(json.contains("Blaj9747"));
        Assert.assertTrue(json.contains("AFYA CARE"));
        Assert.assertTrue(json.contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvbmhjci5yYXN4cC5jb206ODA4MFwvYXBpXC9hdXRoXC9sb2dpbiIsImlhdCI6MTYxMjkzMTk3NiwiZXhwIjoxNjEyOTM1NTc2LCJuYmYiOjE2MTI5MzE5NzYsImp0aSI6IlVDb21HNVpQVlN1Wk9KMFgiLCJzdWIiOjEsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.UzmHbKcgrgIFdxRGfs74Oyb1C1lvgigk5IDcCvePLis"));

        Assert.assertTrue(json.contains("52c9fa36-6b7f-483f-8c4a-1ad033e78618"));
        Assert.assertTrue(json.contains("P"));
        Assert.assertTrue(json.contains("fname"));
        Assert.assertTrue(json.contains("mname"));
        Assert.assertTrue(json.contains("lname"));
        Assert.assertTrue(json.contains("oname"));
        Assert.assertTrue(json.contains("981426-6090"));
        Assert.assertTrue(json.contains("12345"));
        Assert.assertTrue(json.contains("NATIONAL_ID"));
        Assert.assertTrue(json.contains("FEMALE"));
        Assert.assertTrue(json.contains("2020-03-15"));
        Assert.assertTrue(json.contains("Manyara"));
        Assert.assertTrue(json.contains("Kiteto"));
        Assert.assertTrue(json.contains("Ayasanda"));
        Assert.assertTrue(json.contains("Robayambao"));
        Assert.assertTrue(json.contains("255"));
        Assert.assertTrue(json.contains("0754886287"));
        Assert.assertTrue(json.contains("12232131"));
        Assert.assertTrue(json.contains("NATIONAL_ID"));
        Assert.assertTrue(json.contains("Parent"));
        Assert.assertTrue(json.contains("101335-8"));
        Assert.assertTrue(json.contains("0"));
        Assert.assertTrue(json.contains("2020-06-02T07:07:20.000Z"));

    }

}