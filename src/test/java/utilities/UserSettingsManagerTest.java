package utilities;

import org.junit.Test;

/**
 * @author Jean-Paul Labadie
 */
public class UserSettingsManagerTest {

    private static UserSettingsManager usm;
    @Test
    public void testGetInstance() throws Exception {

        usm = UserSettingsManager.getInstance();
        UserSettingsManager.addRemoteSettings("Aspen","jlabadie","aspen.tgen.org",22,"PBS", new String[]{"/scratch"});
    }

    @Test
    public void testGetCurrentRemoteSettings() throws Exception {

    }

    @Test
    public void testSetCurrentRemote() throws Exception {

    }

    @Test
    public void testAddRemoteSettings() throws Exception {

    }

    @Test
    public void testRemoveRemoteSettings() throws Exception {

    }

    @Test
    public void testGetUsername() throws Exception {

    }

    @Test
    public void testGetCurrentServerUrl() throws Exception {

    }
}