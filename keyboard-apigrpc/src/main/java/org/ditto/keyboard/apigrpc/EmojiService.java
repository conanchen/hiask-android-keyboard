package org.ditto.keyboard.apigrpc;

import org.ditto.grpc.emoji.EmojiGrpc;
import org.ditto.grpc.emoji.EmojiOuterClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

/**
 * Created by admin on 2017/8/17.
 */

public class EmojiService {
    private ManagedChannel mChannel;
    private EmojiGrpc.EmojiStub asyncStub;
    private EmojiGrpc.EmojiBlockingStub blockingStub;
    private final static String host = "192.168.0.101";
//    private final static String host = "192.168.1.100";
    private final static int port = 8980;

    public EmojiService() {
        mChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();

        blockingStub = EmojiGrpc.newBlockingStub(mChannel);
        asyncStub = EmojiGrpc.newStub(mChannel);
    }


    public List<EmojiOuterClass.EmojiResponse> getEmojiResponses(long afterLastUpdated) {
        return this.getEmojiResponses(afterLastUpdated, blockingStub);
    }

    /**
     * Blocking unary call example.  Calls getEmojiResponses and prints the response.
     */
    public List<EmojiOuterClass.EmojiResponse> getEmojiResponses(long afterLastUpdated, EmojiGrpc.EmojiBlockingStub blockingStub)
            throws StatusRuntimeException {

        EmojiOuterClass.EmojiRequest request = EmojiOuterClass.EmojiRequest.newBuilder()
                .setAfterLastUpdated(afterLastUpdated)
                .setPageSize(30)
                .build();

        Iterator<EmojiOuterClass.EmojiResponse> emojiResponseIterator = blockingStub
                .withDeadlineAfter(10000, TimeUnit.MILLISECONDS)
                .listEmojis(request);
        List<EmojiOuterClass.EmojiResponse> emojiResponses = new ArrayList<>();
        while (emojiResponseIterator.hasNext()) {
            EmojiOuterClass.EmojiResponse emojiResponse = emojiResponseIterator.next();
            emojiResponses.add(emojiResponse);
        }
        return emojiResponses;

    }
}
