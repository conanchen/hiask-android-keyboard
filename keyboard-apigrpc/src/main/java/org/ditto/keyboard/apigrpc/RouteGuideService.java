package org.ditto.keyboard.apigrpc;

import android.support.v4.util.Pair;
import android.text.TextUtils;

import org.ditto.keyboard.apigrpc.util.RouteGuideUtil;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.routeguideexample.Feature;
import io.grpc.routeguideexample.Point;
import io.grpc.routeguideexample.RouteGuideGrpc;

/**
 * Created by admin on 2017/8/17.
 */

public class RouteGuideService {
    private ManagedChannel mChannel;
    private RouteGuideGrpc.RouteGuideBlockingStub blockingStub;
    private final static String host = "192.168.0.102";
    private final static int port = 8980;

    public RouteGuideService() {
        mChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();

        blockingStub = RouteGuideGrpc.newBlockingStub(mChannel);
        RouteGuideGrpc.newStub(mChannel);
    }

    public Pair<String,StatusRuntimeException> getFeature(int lat, int lon) {
        Pair<String,StatusRuntimeException> result ;
        try {
            String s = getFeature(lat, lon, blockingStub);
            result = Pair.create(s,null);
        }catch (StatusRuntimeException e){
            result = Pair.create(null,e);
        }
        return result;
    }

    /**
     * Blocking unary call example.  Calls getEmojiResponses and prints the response.
     */
    private String getFeature(int lat, int lon, RouteGuideGrpc.RouteGuideBlockingStub blockingStub)
            throws StatusRuntimeException {
        StringBuffer logs = new StringBuffer();
        appendLogs(logs, "*** GetFeature: lat={0} lon={1} from host={2} port={3}", lat, lon,host,port);

        Point request = Point.newBuilder().setLatitude(lat).setLongitude(lon).build();

        Feature feature;
        feature = blockingStub.withDeadlineAfter(5000, TimeUnit.MILLISECONDS).getFeature(request);
        if (RouteGuideUtil.exists(feature)) {
            appendLogs(logs, "Found feature called \"{0}\" at {1}, {2}",
                    feature.getName(),
                    RouteGuideUtil.getLatitude(feature.getLocation()),
                    RouteGuideUtil.getLongitude(feature.getLocation()));
        } else {
            appendLogs(logs, "Found no feature at {0}, {1}",
                    RouteGuideUtil.getLatitude(feature.getLocation()),
                    RouteGuideUtil.getLongitude(feature.getLocation()));
        }
        return logs.toString();
    }

    private static void appendLogs(StringBuffer logs, String msg, Object... params) {
        if (params.length > 0) {
            logs.append(MessageFormat.format(msg, params));
        } else {
            logs.append(msg);
        }
        logs.append(System.lineSeparator());
    }

}
