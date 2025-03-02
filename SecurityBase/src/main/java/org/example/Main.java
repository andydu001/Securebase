package org.example;

import com.huaweicloud.common.util.MD5Util;
import com.huaweicloud.common.util.NetUtil;
import com.huaweicloud.common.util.SecretUtil;
import com.huaweicloud.governance.authentication.securityPolicy.SecurityPolicyProperties;
import org.eclipse.jetty.io.*;
import org.eclipse.jetty.io.content.PathContentSource;
import org.eclipse.jetty.io.content.InputStreamContentSource;
import org.eclipse.jetty.util.thread.Invocable;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.EventListener;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws IOException {
        String netUtil = NetUtil.getLocalHost();
        System.out.println("Local host IP: " + netUtil);
        String md5 = MD5Util.encrypt("Andy");
        System.out.println("MD5 of 'huawei': " + md5);


        String secret = SecretUtil.sha256Encode("55", "B23");
        System.out.println(secret);
        SecurityPolicyProperties securityPolicyProperties = new SecurityPolicyProperties();

        ArrayByteBufferPool pool = new ArrayByteBufferPool();
        pool.acquire(1000, true);
        RetainableByteBuffer.Pooled pooled =  new  RetainableByteBuffer.Pooled(pool, ByteBuffer.allocate(1500));
        int capacity = pooled.capacity();
        System.out.println(capacity);
        pooled.limit(capacity);
        boolean empty = pooled.isEmpty();
        System.out.println(empty);

        ByteBuffer byteBuffer = pooled.getByteBuffer();
        byteBuffer.putChar(0,  'a');
        byteBuffer.putChar(1, Character.valueOf('b'));
        byteBuffer.putChar(2, Character.valueOf('c'));
        byteBuffer.putChar(3, Character.valueOf('d'));
        for(int i = 0; i < byteBuffer.capacity(); i++) {

            StringBuffer sb = new StringBuffer(String.valueOf(byteBuffer.get(i)));

            System.out.println(sb);
        }


        System.out.println(pooled.remaining()+" bytes");

        byte buffer = 69;

        //pooled.add(byteBuffer);

        //pooled.putLong(buffer);
        pooled.put(1, buffer);


        System.out.println(pooled.remaining()+" bytes");

        System.out.println(pooled.capacity()+" bytes");
        System.out.println(pooled.get(1));
        pooled.release();

        PathContentSource poo = new PathContentSource(new File("C:\\Users\\andyd\\OneDrive\\Documents\\MAD.txt").toPath());
        System.out.println(poo.read().capacity());


        MemoryEndPointPipe memoryEndPointPipe = new MemoryEndPointPipe(new ScheduledExecutorScheduler(), new Consumer<Invocable.Task>() {
            @Override
            public void accept(Invocable.Task task) {
                //   task.run();
            }
        }, new SocketAddress() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });

        System.out.println(memoryEndPointPipe.getLocalEndPoint().isOpen());
        System.out.println(memoryEndPointPipe.getRemoteEndPoint().isOpen());

        memoryEndPointPipe.getLocalEndPoint().setConnection(new Connection() {
            @Override
            public void addEventListener(EventListener listener) {

            }

            @Override
            public void removeEventListener(EventListener listener) {

            }

            @Override
            public void onOpen() {

            }

            @Override
            public void onClose(Throwable cause) {

            }

            @Override
            public EndPoint getEndPoint() {
                return new ByteArrayEndPoint();
            }

            @Override
            public void close() {

            }

            @Override
            public boolean onIdleExpired(TimeoutException timeoutException) {
                return false;
            }

            @Override
            public long getMessagesIn() {
                return 0;
            }

            @Override
            public long getMessagesOut() {
                return 0;
            }

            @Override
            public long getBytesIn() {
                return 0;
            }

            @Override
            public long getBytesOut() {
                return 0;
            }

            @Override
            public long getCreatedTimeStamp() {
                return 0;
            }
        });
        boolean secure = memoryEndPointPipe.getLocalEndPoint().isSecure();
        System.out.println(secure);
        String address = String.valueOf(memoryEndPointPipe.getLocalEndPoint().getConnection().getEndPoint().fill(ByteBuffer.allocateDirect(89)));
        System.out.println(address);




        InputStreamContentSource inputStreamContentSource = new InputStreamContentSource(new FileInputStream(new File("C:\\Users\\andyd\\OneDrive\\Documents\\MAD.txt")));
        System.out.println(inputStreamContentSource.read().capacity());

    }
}