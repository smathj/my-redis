package L4_sorted_set;

import common.Common;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        try(JedisPool pool = Common.getResource()) {
            try(Jedis jedis = pool.getResource()) {
                // sorted set
                Map scores = new HashMap<String, Double>();
                scores.put("users1", 100.0);
                scores.put("users2", 30.0);
                scores.put("users3", 50.0);
                scores.put("users4", 80.0);
                scores.put("users5", 15.0);

                jedis.zadd("game2:scores", scores);

                // 전체 조회
                List<String> zrange = jedis.zrange("game2:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);

                // 토탈 카운트
                System.out.println(jedis.zcard("game2:scores"));

                // users5 의 점수를 100 증분
                jedis.zincrby("game2:scores", 100.0, "users5");

                // 키 뿐만 아니라 값(점수)도 출력
                List<Tuple> tuples = jedis.zrangeWithScores("game2:scores", 0, Long.MAX_VALUE);
                tuples.forEach(i -> System.out.println("%s %f".formatted(i.getElement(), i.getScore())));




                // 오름 차순 조회
                //? ZRANGE game2:scores 0 +inf byscore limit 0 100 withscores
                List<Tuple> ascTupleList = jedis.zrangeByScoreWithScores("game2:scores", 0, Long.MAX_VALUE, 0, Integer.MAX_VALUE);
                System.out.println("오름 차순 조회");
                for (Tuple tuple : ascTupleList) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }

                System.out.println();
                System.out.println();


                // 내림 차순 조회
                //? ZRANGE game2:scores +inf 0 byscore rev limit 0 100 withscores
                List<Tuple> descTupleList = jedis.zrevrangeByScoreWithScores("game2:scores", Long.MAX_VALUE, 0, 0, Integer.MAX_VALUE);
                System.out.println("내림 차순 조회");
                for (Tuple tuple : descTupleList) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }


            }

        }



    }

}
