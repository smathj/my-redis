package L1_strings;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class Main {

    public static void main(String[] args) {
            System.out.println("Hello world!");

            try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
                try(Jedis jedis = jedisPool.getResource()) {
                    jedis.set("users:300:email", "kim@fastcampus.co.kr");
                    jedis.set("users:300:name", "kim 00");
                    jedis.set("users:300:age", "100");

                    var userEmail = jedis.get("users:300:email");
                    System.out.println(userEmail);

                    List<String> userInfo = jedis.mget("users:300:email", "users:300:name", "users:300:age");
                    userInfo.forEach(System.out::println);

                    long counter = jedis.incr("counter");
                    System.out.println("counter = " + counter);

                    counter = jedis.incrBy("counter", 10L);
                    System.out.println("counter = " + counter);

                    counter = jedis.decr("counter");
                    System.out.println("counter = " + counter);

                    counter = jedis.decrBy("counter", 20L);
                    System.out.println("counter = " + counter);


                    /*
                    ! 실제 나가는 명령어, 한번에 여러 요청의 set 을 요청한다
                    SET "users:400:email" "greg@fastcampush.co.kr"
                    SET "users:400:name" "greg"
                    SET "users:400:age" "15"
                     */
                    Pipeline pipelined = jedis.pipelined();
                    pipelined.set("users:400:email", "greg@fastcampush.co.kr");
                    pipelined.set("users:400:name", "greg");
                    pipelined.set("users:400:age", "15");
                    List<Object> objects = pipelined.syncAndReturnAll();
                    objects.forEach(i -> System.out.println(i.toString()));

                }
            }
        }

}
