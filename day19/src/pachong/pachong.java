package pachong;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class pachong {
    public static void main(String[] args) throws IOException {
        //爬虫
        Random ran = new Random ( );
        HttpURLConnection connection = (HttpURLConnection) new URL ("https://tieba.baidu.com/p/2256306796?red_tag=1781367364/html").openConnection();
        InputStream in = connection.getInputStream();
        BufferedReader buffer = new BufferedReader ( new InputStreamReader ( in ) );
        while (true){
            String line = buffer.readLine ( );
            if (line==null){
                break;
            }else {
                if (line .contains ( "<img" )){
                    download(line ,ran);
                }
            }
        }
    }

    private static void download(String line,Random ran) throws IOException {
        int imgindex = line.indexOf ( "<img" );
        String s1 = line.substring ( imgindex );
        int srcindex = s1.indexOf ( "src=" );
        String s2 = s1.substring ( srcindex+5);
        int xindex = s2.indexOf ( "\"" );
        String s3 = s2.substring ( 0, xindex );
        System.out.println (s3 );
        if (s3.startsWith ( "http" )|s3.startsWith ( "http" )){
            HttpURLConnection url = (HttpURLConnection)new URL ( s3 ).openConnection ( );
            InputStream in = url.getInputStream ( );
            String i = ran.nextInt ( )+"";
            FileOutputStream out = new FileOutputStream ( "G:\\jpg\\" + i + ".png" );

            while (true){
                byte[] bytes = new byte[1024 * 8];
                int read = in.read ( bytes );
                if (read==-1){
                    break;
                }
                out.write ( bytes,0,read );
            }
        }
        String str = s2.substring ( xindex );
        if (str.contains ( "<img" )){
            download ( str,ran );
        }
    }
}