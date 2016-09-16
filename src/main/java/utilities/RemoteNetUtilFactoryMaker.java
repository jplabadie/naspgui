package utilities;

/**
 * @author Jean-Paul Labadie
 */
public class RemoteNetUtilFactoryMaker{

    //private static AbstractRemoteNetUtilFactory factory = null;
    public static AbstractRemoteNetUtilFactory getFactory(){

        return new RemoteNetUtilFactory();
    }
}

