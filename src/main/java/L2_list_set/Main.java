package L2_list_set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {


        try(JedisPool pool = new JedisPool("localhost", 6379)) {
            Jedis jedis = pool.getResource();

            //! list
            // 1. stack 구현 rpush rpop
            System.out.println("======= 스택 구현 =======");
            jedis.rpush("stack1", "aaaa");
            jedis.rpush("stack1", "bbbb");
            jedis.rpush("stack1", "cccc");

            System.out.println(jedis.rpop("stack1"));   // cccc
            System.out.println(jedis.rpop("stack1"));   // bbbb
            System.out.println(jedis.rpop("stack1"));   // aaaa

            // 2. queue 구현 rpush, lpop
            System.out.println("======= 큐 구현 =======");
            jedis.rpush("queue2", "zzzz");
            jedis.rpush("queue2", "aaaa");
            jedis.rpush("queue2", "cccc");

            System.out.println(jedis.lpop("queue2"));   // zzzz
            System.out.println(jedis.lpop("queue2"));   // aaaa
            System.out.println(jedis.lpop("queue2"));   // cccc


            // 3. block brpop, blpop, 블로킹 오른쪽 팝, 블로킹 왼쪽 팝
            /*
            while (true) {
                List<String> blpop = jedis.blpop(10, "queue:blocking");
                if (blpop != null) {
                    blpop.forEach(System.out::println);
                }
            }
            */

            //! set
            System.out.println("======= set =======");
            jedis.sadd("users:500:follow", "100", "200", "300"); // add
            jedis.srem("users:500:follow", "100");  // remove

            Set<String> smembers = jedis.smembers("users:500:follow");
            smembers.forEach(System.out::println);  // 200 300

            System.out.println(jedis.sismember("users:500:follow", "200")); // true
            System.out.println(jedis.sismember("users:500:follow", "120")); // false


            System.out.println(jedis.scard("users:500:follow"));    // 2

            Set<String> sinter = jedis.sinter("users:500:follow", "users:100:follow");
            sinter.forEach(System.out::println);


        } catch(Exception e) {
            e.printStackTrace();
        }






    }
}
