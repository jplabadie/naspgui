package utilities;

import org.junit.Assert;
import org.junit.Test;

import java.security.Provider;
import java.security.Security;

/**
 * @author Jean-Paul Labadie
 */
public class LocalSecurityManagerTest {

    @Test
    public void testProviders() throws Exception{
        Provider providers[] = Security.getProviders();

        for(int i = 0; i < providers.length; i++){

            Provider provider = providers[i];
            System.out.println(provider);
        }
    }


    @Test
    public void testEncrypt() throws Exception {
        String t = LocalSecurityManager.createHash("monkey");

        Assert.assertNotEquals(t,"monkey");
    }

    @Test
    public void testDecrypt() throws Exception {
        String hash = LocalSecurityManager.createHash("balloon");
        String pass = "balloon";
        Boolean x = LocalSecurityManager.verifyPassword(pass, hash);
        Assert.assertTrue(x);
        System.out.println(x);
    }

    @Test
    public void testDecryptTwoInstances() throws Exception {
        String hash = LocalSecurityManager.createHash("balloon");
        String pass = "balloon";
        Boolean x = LocalSecurityManager.verifyPassword(pass, hash);
        Assert.assertTrue(x);
        System.out.println(x);

        String hash2 = LocalSecurityManager.createHash("balloon");
        String pass2 = "balloon";
        Boolean y = LocalSecurityManager.verifyPassword(pass2, hash2);
        Assert.assertTrue(y);
        System.out.println(y);

        String hash3 = LocalSecurityManager.createHash("balloon");
        String pass3 = "balloon";
        Boolean z = LocalSecurityManager.verifyPassword(pass2, hash2);
        Assert.assertTrue(z);
        System.out.println(z);

        System.out.println(hash +" : " +hash2 + " : " + hash3);
    }

    @Test
    public void testStore() throws Exception {


    }
}