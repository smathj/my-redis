import redis.clients.jedis.JedisPool;

public class Common {

    public static JedisPool getResource() {
        String url = "127.0.0.1";
        int port = 6379;
        return new JedisPool(url, port);
    }
}
