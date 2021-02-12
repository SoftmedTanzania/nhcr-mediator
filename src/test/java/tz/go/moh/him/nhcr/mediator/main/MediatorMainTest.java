package tz.go.moh.him.nhcr.mediator.main;

import org.junit.Assert;
import org.junit.Test;
import org.openhim.mediator.engine.MediatorConfig;
import tz.go.moh.him.nhcr.mediator.MediatorMain;

import java.lang.reflect.Method;

/**
 * Contains tests for the {@link MediatorMain} class.
 */
public class MediatorMainTest {

    /**
     * Test the mediator main class loading the configuration.
     *
     * @throws Exception
     */
    @Test
    public void mediatorMainTest() throws Exception {

        Method loadConfigMethod = MediatorMain.class.getDeclaredMethod("loadConfig", String.class);

        loadConfigMethod.setAccessible(true);
        MediatorConfig mediatorConfig = (MediatorConfig) loadConfigMethod.invoke(null, "src/test/resources/mediator.properties");

        Assert.assertEquals("localhost", mediatorConfig.getServerHost());
        Assert.assertEquals(new Integer(3014), mediatorConfig.getServerPort());
        Assert.assertEquals(new Integer(60000), mediatorConfig.getRootTimeout());
        Assert.assertTrue(mediatorConfig.getHeartsbeatEnabled());
    }
}
