package L0_sample;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Sample {

    public static void main(String[] args) {


        try(JedisPool pool = new JedisPool("localhost", 6379)) {

            Jedis jedis = pool.getResource();

            // string
            jedis.set("foo", "bar");
            System.out.println(jedis.get("foo"));

            // hash
            Map<String, String> hash = new HashMap<>();
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");
            jedis.hset("user-session:123", hash);
            System.out.println(jedis.hgetAll("user-session:123"));


        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
