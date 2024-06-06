package L3_hash;

import common.Common;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        try(JedisPool pool = Common.getResource() ) {
            Jedis jedis = pool.getResource();

            // hset
            // name = greg2
            jedis.hset("users:2:info", "name", "greg2");

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("email", "greg3@nover.com");
            userInfo.put("phone", "010-XXXX-XXXX");

            jedis.hset("users:2:info", userInfo);

            // hdel
            jedis.hdel("users:2:info", "phone");


            // get, getall
            System.out.println(jedis.hget("users:2:info", "email"));

            System.out.println("===============================================");
            Map<String, String> user2Info = jedis.hgetAll("users:2:info");
            user2Info.forEach((k, v) -> System.out.printf("%s %s%n", k, v));
            System.out.println("===============================================");

            // hincrBy
            jedis.hincrBy("users:2:info", "visits", 30);

            System.out.println("===============================================");
            user2Info = jedis.hgetAll("users:2:info");
            user2Info.forEach((k, v) -> System.out.printf("%s %s%n", k, v));
            System.out.println("===============================================");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
