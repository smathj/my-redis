package L4_sorted_set;

import common.Common;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paging {

    public static void main(String[] args) {

        try(JedisPool pool = Common.getResource()) {
            try(Jedis jedis = pool.getResource()) {

                // 오름 차순 조회
                //? ZRANGE game2:scores 0 +inf byscore limit 0 100 withscores
                List<Tuple> ascTupleList = jedis.zrangeByScoreWithScores("game2:scores", 0, Long.MAX_VALUE, 0, Integer.MAX_VALUE);
                System.out.println("오름 차순 조회");
                for (Tuple tuple : ascTupleList) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }
/*

                System.out.println();
                System.out.println();


                // 내림 차순 조회
                //? ZRANGE game2:scores +inf 0 byscore rev limit 0 100 withscores
                List<Tuple> descTupleList = jedis.zrevrangeByScoreWithScores("game2:scores", Long.MAX_VALUE, 0, 0, Integer.MAX_VALUE);
                System.out.println("내림 차순 조회");
                for (Tuple tuple : descTupleList) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }
*/


                System.out.println();
                System.out.println();

                // 토탈 카운트
                int totalCount = (int) jedis.zcard("game2:scores");

                // 한페이지당 보여줄 게시글 갯수
                int viewCount = 1;

                // 1 페이지
                int nowPage = 1;

                // 1 페이지 조회
                List<Tuple> firstPage = jedis.zrangeByScoreWithScores("game2:scores", 0, Long.MAX_VALUE, (nowPage -1), viewCount);

                System.out.println("==> " + nowPage + " 페이지 조회");
                for (Tuple tuple : firstPage) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }

                nowPage++;
                List<Tuple> secondPage = jedis.zrangeByScoreWithScores("game2:scores", 0, Long.MAX_VALUE, (nowPage -1), viewCount);
                System.out.println("==> " + nowPage + " 페이지 조회");
                for (Tuple tuple : secondPage) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }

                nowPage++;
                List<Tuple> thirdPage = jedis.zrangeByScoreWithScores("game2:scores", 0, Long.MAX_VALUE, (nowPage -1), viewCount);
                System.out.println("==> " + nowPage + " 페이지 조회");
                for (Tuple tuple : thirdPage) {
                    System.out.println("%s : %f".formatted(tuple.getElement(), tuple.getScore()));
                }









            }

        }



    }
}
