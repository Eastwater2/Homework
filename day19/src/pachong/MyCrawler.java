package pachong;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCrawler {
    public static void main(String[] args) throws IOException {
        String url = "https://tieba.baidu.com/p/2256306796?red_tag=1781367364";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        ExecutorService service = Executors.newFixedThreadPool(5);
        String html = fetch(connection);
        Matcher matcher = PATTERN.matcher(html);
        while (matcher.find()) {
            String imageURL = matcher.group(1);
            service.submit( ()-> {
                try {
                    download(imageURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static void download(String imageURL) throws IOException {
        HttpURLConnection c = (HttpURLConnection) new URL(imageURL).openConnection();
        String img = imageURL.substring(imageURL.lastIndexOf("/") + 1);
        try (
                InputStream in = c.getInputStream();
                OutputStream out = new FileOutputStream("G:\\images\\" + img)
        ) {
            byte[] bytes = new byte[1024 * 1024];
            while (true) {
                int len = in.read(bytes);
                if (len == -1) {
                    break;
                }
                out.write(bytes, 0, len);
            }
        }
    }

    static final Pattern PATTERN = Pattern.compile("<img class=\"BDE_Image\" src=\"(.*?)\">");
    private static String fetch(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder(1024*1024);
        try (BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))
        ) {
            while(true) {
                String line = reader.readLine();
                if(line != null) {
                    break;
                }
                sb.append(line);
            }
        } catch(IOException e) {

        }
        return sb.toString();
    }

}
