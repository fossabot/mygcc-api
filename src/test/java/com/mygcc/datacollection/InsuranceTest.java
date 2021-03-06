package com.mygcc.datacollection;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

import javax.ws.rs.core.Application;

/**
 * Insurance Resource test class.
 */
public final class InsuranceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(Insurance.class);
    }

    /**
     * Test Insurance response status with valid Id.
     * @throws Exception error occurred
     */
    @Test
    public void testValidInsuranceId() throws Exception {
        Assume.assumeTrue(System.getenv("myGCC_username") != null
        && System.getenv("myGCC_password") != null);

        Insurance in = new Insurance(new Token(
                System.getenv("myGCC_username"),
                System.getenv("myGCC_password")
        ));

        Assert.assertNotNull(in.getInsuranceData());
    }
}
