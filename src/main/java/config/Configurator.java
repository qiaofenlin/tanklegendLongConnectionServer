package config;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Created by  qiao
 * @date 18-2-22 下午2:08
 */

public class Configurator {
    private static Map<String, String> configs;

//    private static final String SERVERPATH = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/resources/tanklegendNettyServer.properties";
    private static final String LONGCONNECTIONSERVERPATH = "/home/qiao/IdeaProjects/tanklegendnetty/src/main/resources/LongConnectionServer.properties";

    static {
        configs = new ConcurrentHashMap();
    }

    public static void init() {
        BufferedReader serverIn = null;
        String conf = "";

        try {
            serverIn = new BufferedReader(new FileReader(LONGCONNECTIONSERVERPATH));
            while ((conf = serverIn.readLine()) != null) {
                String[] pair = conf.split("=");
                if (pair.length != 2) {
                    throw new Exception("config error");
                } else {
                    configs.put(pair[0], pair[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //连接服务器 获得nettyserver的地址和端口号.
    public static String getServerHost() throws Exception {
        String host = configs.get(ConfigKeyword.LONG_CONNECTION_SERVER_HOST);
        if (host == null) {
            throw new Exception("Server host Not Found");
        }
        return host;
    }

    public static int getServerPort() throws Exception {
        String str = configs.get(ConfigKeyword.LONG_CONNECTION_SERVER_PORT);
        int port = 0;
        try {
            if (str != null) {
                port = Integer.parseInt(str);
            } else {
                throw new Exception("Server port Not Found");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Server port format error!");
        }
        return port;
    }

}
