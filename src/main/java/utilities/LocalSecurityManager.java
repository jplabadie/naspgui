package utilities;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 *
 * This class and its methods are not currently used by the GUI tool.
 *
 * Provides an optional utility for securing user login to the GUI.
 * Also provides optional 2-way encryption of locally saved user authentication for remote services.
 * No guarantee is given with these optional functions, and security remains the responsibility of the user.
 */
public class LocalSecurityManager
{

    private static LocalSecurityManager instance = new LocalSecurityManager();

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    // These constants may be changed without breaking existing hashes.
    public static final int SALT_BYTE_SIZE = 24;
    public static final int HASH_BYTE_SIZE = 18;
    public static final int PBKDF2_ITERATIONS = 64000; //use a minimum of 10000

    // These constants define the encoding and may not be changed.
    public static final int HASH_SECTIONS = 5;
    public static final int HASH_ALGORITHM_INDEX = 0;
    public static final int ITERATION_INDEX = 1;
    public static final int HASH_SIZE_INDEX = 2;
    public static final int SALT_INDEX = 3;
    public static final int PBKDF2_INDEX = 4;

    private static String username;
    private static String password;

    private static KeyStore initKeyStore(String keystoreFileOrPath, String password) throws Exception {

        File file = new File(keystoreFileOrPath);
        final KeyStore keyStore = KeyStore.getInstance("JCEKS");

        if (file.exists()) {
            // .keystore file already exists => load it
            keyStore.load(new FileInputStream(file), password.toCharArray());
        } else {
            // .keystore file not created yet => create it
            keyStore.load(null, null);
            keyStore.store(new FileOutputStream(keystoreFileOrPath), password.toCharArray());
        }

        return keyStore;
    }

    /**
     *
     * @return Singleton instance
     */
    public static LocalSecurityManager getInstance(){
        return instance;
    }

    public static String getUsername(){
        return username;
    }

    public static String getPassword(){
        return password;
    }

    public static void setUsername(String usr){
        username = usr;
    }

    public static void setPassword(String pass){
        password = pass;
    }

    /**
     *
     */
    public static void saveSecretKey(){

        final String keyStoreFile = "output/javacirecep.keystore";
        KeyStore keyStore = null;
        try {
            keyStore = initKeyStore(keyStoreFile, "javaci123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Base64.Encoder encoder = Base64.getEncoder();

        // generate a secret key for AES encryption
        SecretKey secretKey = null;
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Failed to generate key with specified algorithm.");
        }
        System.out.println("Stored Key: " + encoder.encodeToString(secretKey.getEncoded()));

        // store the secret key
        KeyStore.SecretKeyEntry keyStoreEntry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("pw-secret".toCharArray());
        try {
            keyStore.setEntry("mySecretKey", keyStoreEntry, keyPassword);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyStore.store(new FileOutputStream(keyStoreFile), "javaci123".toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param password
     */
    public static void loadHash(char[] password){

        final String keyStoreFile = "output/javacirecep.keystore";
        KeyStore keyStore = null;
        try {
            keyStore = initKeyStore(keyStoreFile, "javaci123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // retrieve the stored key back
        KeyStore.Entry entry = null;

        try {
            KeyStore.ProtectionParameter protParameter =
                    new KeyStore.PasswordProtection(password);
            entry = keyStore.getEntry("mySecretKey", protParameter);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SecretKey keyFound = ((KeyStore.SecretKeyEntry) entry).getSecretKey();
        System.out.println("Found Key: " + (keyFound));
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    static public class InvalidHashException extends Exception {
        public InvalidHashException(String message) {
            super(message);
        }
        public InvalidHashException(String message, Throwable source) {
            super(message, source);
        }
    }

    /**
     *
     */
    @SuppressWarnings("serial")
    static public class CannotPerformOperationException extends Exception {
        public CannotPerformOperationException(String message) {
            super(message);
        }
        public CannotPerformOperationException(String message, Throwable source) {
            super(message, source);
        }
    }


    /**
     *
     * @param password
     * @return
     * @throws CannotPerformOperationException
     */
    public static String createHash(String password)
            throws CannotPerformOperationException
    {
        return createHash(password.toCharArray());
    }

    /**
     *
     * @param password
     * @return
     * @throws CannotPerformOperationException
     */
    public static String createHash(char[] password)
            throws CannotPerformOperationException
    {
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = getSecretKey(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        int hashSize = hash.length;

        // format: algorithm:iterations:hashSize:salt:hash
        String parts = "sha1:" +
                PBKDF2_ITERATIONS +
                ":" + hashSize +
                ":" +
                toBase64(salt) +
                ":" +
                toBase64(hash);
        return parts;
    }

    /**
     *
     * @param password
     * @param correctHash
     * @return
     * @throws CannotPerformOperationException
     * @throws InvalidHashException
     */
    public static boolean verifyPassword(String password, String correctHash)
            throws CannotPerformOperationException, InvalidHashException
    {
        return verifyPassword(password.toCharArray(), correctHash);
    }

    /**\
     *
     * @param password
     * @param correctHash
     * @return
     * @throws CannotPerformOperationException
     * @throws InvalidHashException
     */
    public static boolean verifyPassword(char[] password, String correctHash)
            throws CannotPerformOperationException, InvalidHashException
    {
        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        if (params.length != HASH_SECTIONS) {
            throw new InvalidHashException(
                    "Fields are missing from the password hash."
            );
        }

        // Currently, Java only supports SHA1.
        if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
            throw new CannotPerformOperationException(
                    "Unsupported hash type."
            );
        }

        int iterations = 0;
        try {
            iterations = Integer.parseInt(params[ITERATION_INDEX]);
        } catch (NumberFormatException ex) {
            throw new InvalidHashException(
                    "Could not parse the iteration count as an integer.",
                    ex
            );
        }

        if (iterations < 1) {
            throw new InvalidHashException(
                    "Invalid number of iterations. Must be >= 1."
            );
        }


        byte[] salt = null;
        try {
            salt = fromBase64(params[SALT_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                    "Base64 decoding of salt failed.",
                    ex
            );
        }

        byte[] hash = null;
        try {
            hash = fromBase64(params[PBKDF2_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                    "Base64 decoding of getSecretKey output failed.",
                    ex
            );
        }


        int storedHashSize = 0;
        try {
            storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
        } catch (NumberFormatException ex) {
            throw new InvalidHashException(
                    "Could not parse the hash size as an integer.",
                    ex
            );
        }

        if (storedHashSize != hash.length) {
            throw new InvalidHashException(
                    "Hash length doesn't match stored hash length."
            );
        }

        // Compute the hash of the provided password, using the same salt,
        // iteration count, and hash length
        byte[] testHash = getSecretKey(password, salt, iterations, hash.length);
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    private static boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    /**
     *
     * @param password
     * @param salt
     * @param iterations
     * @param bytes
     * @return
     * @throws CannotPerformOperationException
     */
    private static byte[] getSecretKey(char[] password, byte[] salt, int iterations, int bytes)
            throws CannotPerformOperationException
    {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new CannotPerformOperationException(
                    "Hash algorithm not supported.",
                    ex
            );
        } catch (InvalidKeySpecException ex) {
            throw new CannotPerformOperationException(
                    "Invalid key spec.",
                    ex
            );
        }
    }

    /**
     *
     * @param hex input base64 encoded string
     * @return output binary byte array
     * @throws IllegalArgumentException
     */
    private static byte[] fromBase64(String hex)
            throws IllegalArgumentException
    {
        return DatatypeConverter.parseBase64Binary(hex);
    }

    /**
     *
     * @param array
     * @return
     */
    private static String toBase64(byte[] array)
    {
        return DatatypeConverter.printBase64Binary(array);
    }


    /**
     *
     * @param property
     * @param password
     * @param salt
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private static String decrypt(String property, char[] password, byte[] salt) throws GeneralSecurityException, IOException {

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(password));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(salt, 20));
        return new String(pbeCipher.doFinal(fromBase64(property)), "UTF-8");
    }


}