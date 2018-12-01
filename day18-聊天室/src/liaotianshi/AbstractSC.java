package liaotianshi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class AbstractSC {

    // cmd 命令类型， content 消息内容,
    public void send(int cmd, String content, OutputStream out) throws IOException {
        // 协议： 就是客户端与服务器端约定的一种规则
        // 第一个字节 ： 命令类型
        // 2，3 字节： 内容长度   10
        // 剩下的是内容
        out.write(cmd); // 第一个字节 ： 命令类型
        byte[] bytes = content.getBytes("utf-8");
        int length = bytes.length; // 300
        // 0x  23 45
        out.write(0xFF & (length >> 8)); // 发送长度的高位
        out.write(0xFF & length); // 发送长度的低位

        out.write(bytes); // 发送字节内容
    }

    public void receive(Socket socket, InputStream in, OutputStream out) throws IOException {
        while(true) {
            int cmd = in.read();
            if(cmd == -1) break;

            int hi = in.read();
            int lo = in.read();
            int length = (hi << 8) + lo;

            byte[] bytes = new byte[length];
            in.read(bytes);

            String content = new String(bytes, "utf-8");
            handle(cmd, content, socket, in, out);
        }
    }

    protected abstract void handle(int cmd, String content, Socket socket, InputStream in, OutputStream out) throws IOException;
}
