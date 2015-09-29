package testCases.stepic;

import base.CaseConfig;
import base.TestCase;
import base.TestException;
import testCases.websockets.WebsocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author v.chibrikov
 */
public class HW04 implements TestCase {
    private static final int waitTime = 5000;
    private static final int waitTick = 10;

    public boolean test(CaseConfig cfg) {
        try {
            String url = "ws://" + cfg.getHost() + ":" + cfg.getPort() + "/chat";
            final WebsocketClient clientEndPoint = new WebsocketClient(new URI(url));
            final AtomicBoolean result = new AtomicBoolean(false);
            final String testMessage = "tester:test!";
            clientEndPoint.addMessageHandler(message -> {
                if (message.contains(testMessage)) {
                    result.set(true);
                }
            });
            clientEndPoint.sendMessage(testMessage);

            long timeStart = new Date().getTime();
            while (!result.get() && new Date().getTime() < timeStart + waitTime) {
                Thread.sleep(waitTick);
            }

            if (!result.get()) {
                System.out.println("No answer from server. Time passed: " + waitTime + " milliseconds");
            }

            return result.get();
        } catch (InterruptedException | URISyntaxException e) {
            throw new TestException(e);
        }

    }
}