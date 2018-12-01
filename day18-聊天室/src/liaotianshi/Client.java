package liaotianshi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends AbstractSC {

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Socket socket = new Socket("localhost", 6666);

        System.out.println("请输入您的昵称:");
        Scanner s = new Scanner(System.in);
        String name = s.nextLine();
        client.send(1,name, socket.getOutputStream());

        new Thread(()->{
            try (OutputStream out = socket.getOutputStream()) {
                Scanner scanner = new Scanner(System.in);
                show();
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    char cmd = line.charAt(0);
                    switch(cmd) {
                        case '2':
                            client.send(2, "", out);
                            break;
                        case '3':
                            String content = line.substring(2);
                            client.send(3, content, out);
                            break;
                        case '4':
                            String c2 = line.substring(2);
                            client.send(4, c2, out);
                            break;
                        default:
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try (InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream()
            ) {
                client.receive(socket, in, out);
            } catch (IOException e) {
            }
        }).start();
    }


    @Override
    protected void handle(int cmd, String content, Socket socket, InputStream in, OutputStream out) {
        switch (cmd) {
            case 5:
                System.out.println(content);
                break;
            case 6:
                System.out.println(content);
                break;
            case 7:
                System.out.println(content);
                break;
            case 8:
                System.out.println(content);
                break;
        }
    }
    private static void show() {
        System.out.println ("--------------------------------------------------------------" );
        System.out.println ("************************欢迎使用聊天室*************************" );
        System.out.println ("功能如下" );
        System.out.println ("2 获取当前聊天室成员" );
        System.out.println ("3 内容 [群聊]" );
        System.out.println ("4 对方姓名 内容 [私聊]" );
    }
}
