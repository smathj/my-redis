package L6_bitmap;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        try(var jedisPool = new JedisPool("127.0.0.1", 6379)) {
                    try(var jedis = jedisPool.getResource()) {

                        jedis.setbit("request-somepage-20230305", 100, true);
                        jedis.setbit("request-somepage-20230305", 200, true);
                        jedis.setbit("request-somepage-20230305", 300, true);

                        System.out.println(jedis.getbit("request-somepage-20230305", 100));
                        System.out.println(jedis.getbit("request-somepage-20230305", 50));

                        System.out.println(jedis.bitcount("request-somepage-20230305"));


                        /*
                        memory usage request-somepage-set-20230306
                        4248776
                        memory usage request-somepage-bit-20230306
                        16456
                         */
                        // bitmap vs set
                        Pipeline pipelined = jedis.pipelined();
                        IntStream.rangeClosed(0, 100000).forEach(i -> {
                            pipelined.sadd("request-somepage-set-20230306", String.valueOf(i), "1");
                            pipelined.setbit("request-somepage-bit-20230306", i, true);

                            // 천개 단위로 데이터 보내기
                            if (i % 1000 == 0) {
                                pipelined.sync();
                            }

                        });
                        pipelined.sync();
                    }
                }


    }
}
