package utilities;

public class RemoteNetUtilFactory extends AbstractRemoteNetUtilFactory{

    @Override
    public RemoteNetUtil createRemoteNetUtil() {
        return new DefaultRemoteNetUtil();
    }
}
