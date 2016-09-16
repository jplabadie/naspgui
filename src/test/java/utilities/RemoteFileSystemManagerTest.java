package utilities;

import org.junit.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

/**
 *
 * Unit and integration tests for the RemoteFileSystemManager class
 *
 * @author Jean-Paul Labadie
 */
public class RemoteFileSystemManagerTest {

    private static RemoteFileSystemManager rfsm;

    @BeforeClass
    public static void init() throws Exception{
        rfsm = RemoteFileSystemManager.getInstance();
        rfsm.init("usrname", "password", "aspen.tgen.org", 22);
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInit() throws Exception {
    }

    @Test
    public void testGetDirectory() throws Exception {

        DirectoryStream<Path> paths = rfsm.getDirectory("/scratch");

        for(Path x : paths){
            System.out.println(x.toString());
        }
    }

    @Test
    public void testIsConnected()throws IOException{

    }

    @Test
    public void testGetRootAsFile() throws IOException {
        Path root = rfsm.getRootAsPath();
        System.out.println(root.toString());
        Assert.assertFalse(root.toFile().isFile());
        Assert.assertEquals("/",root.toFile().getPath());
    }

    @Test
    public void testGetRootAsPath() throws Exception {

    }

    @Test
    public void testGetDirAsPath() throws Exception {

    }

    @Test
    public void testIsConnected1() throws Exception {

    }

    @Test
    public void testGetInstance() throws Exception {

    }
}