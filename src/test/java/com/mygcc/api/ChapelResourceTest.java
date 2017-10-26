package com.mygcc.api;

import com.mygcc.datacollection.Authorization;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assume;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public final class ChapelResourceTest extends JerseyTest {
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(WelcomeResource.class);
    }

    /**
     * Test when token is null.
     */
    @Test
    public void testNullAuthorization() {
        ChapelResource chap = new ChapelResource();
        Response r = chap.getChapelData(null);
        assertEquals("status should be 400", Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus());
    }

    /**
     * Test when key is null.
     */
    @Test
    public void testFakeAuth() {
        ChapelResource chap = new ChapelResource();
        Response r = chap.getChapelData("asdf");
        assertEquals("token should be invalid; status should be 400", Response.Status.BAD_REQUEST.getStatusCode(), r.getStatus());
    }

    /**
     * Test when key is null.
     */
    @Test
    public void testWorkingKey() {
        Assume.assumeTrue(System.getenv("myGCC-username") != null
                && System.getenv("myGCC-password") != null
                && System.getenv("initvect") != null
                && System.getenv("enckey") != null);
        String un = System.getenv("myGCC-username");
        String pw = System.getenv("myGCC-password");

        Authorization auth = new Authorization(un, pw);
        try {
            String token = auth.encryptToken();

            ChapelResource chap = new ChapelResource();
            Response r = chap.getChapelData(token);
            assertEquals("test working key", Response.Status.OK.getStatusCode(), r.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}