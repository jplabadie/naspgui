package utilities;

/**
 * Defines the methods needed by RemoteNetUtilFactory concrete subclasses in the AbstractFactory pattern
 */
public abstract class AbstractRemoteNetUtilFactory {
    public abstract RemoteNetUtil createRemoteNetUtil();
}
