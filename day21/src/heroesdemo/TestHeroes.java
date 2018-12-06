package heroesdemo;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//用Stream流的方法，2.3.4.5题目有思路总是报错后3题无思路，再看了老师的代码后完成了题目。
public class TestHeroes {
    // 1. 找到武将中武力前三的hero对象, 提示流也可以排序
// 2. 按出生地分组
// 3. 找出寿命前三的武将
// 4. 女性寿命最高的
// 5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超
// 6. 按各个年龄段分组： 0~20, 2140, 41~60, 60以上
// 7. 按武力段分组： >=90， 80~89, 70~79, <70
// 8. 按出生地分组后，统计各组人数
    public static void main(String[] args) throws IOException {
        Stream<String> lines = Files.lines(Paths.get("heroes.txt"), Charset.forName("utf-8"));

        List<Heroes> list = lines.map(str -> str.split("\t")).map(array -> new Heroes(Integer.parseInt((array[0])),
                array[1], array[2], array[3],
                Integer.parseInt(array[4]),
                Integer.parseInt(array[5]),
                Integer.parseInt(array[6]))).collect(Collectors.toList());
        //遍历武将信息
        // list2.stream().forEach(heroes -> System.out.println(heroes));
        //1. 找到武将中武力前三的hero对象, 提示流也可以排序
        Stream<Heroes> heroesStream = list.stream().sorted(new Comparator<Heroes>() {
            @Override
            public int compare(Heroes o1, Heroes o2) {
                int a = o1.getPower() - o2.getPower();
                return -a;
            }
        });
        heroesStream.limit(3).forEach(heroes -> System.out.println(heroes.getName() + "\t" + heroes.getPower()));
        //5. 找出武力排名前三  100, 99, 97 97 ==> 4个人 吕布", "张飞", "关羽", "马超"
        Set<Integer> topPowers = list.stream().map(heroes -> heroes.getPower()).sorted((p1, p2) -> p2 - p1).limit(3).collect(Collectors.toSet());
        list.stream().filter((heroes) -> topPowers.contains(heroes.getPower())).forEach((heroes) -> System.out.println(heroes.getName()));

        // 2. 按出生地分组
        Map<String, List<Heroes>> g1 = list.stream().collect(Collectors.groupingBy((h) -> h.getLoc()));
        g1.forEach((k, v) -> { System.out.println(k + ":" + v.stream().map(heroes -> heroes.getName()).collect(Collectors.toList()));
        });
        // 3. 找出寿命前三的武将
        list.stream().sorted((h1, h2) -> (h2.getDeath() - h2.getBirth()) - (h1.getDeath() - h1.getBirth())).limit(3).forEach(h -> System.out.println(h.getName()));
        // 4. 女性寿命最高的
        list.stream().filter(h -> h.getSex().equals("女")).sorted((h1, h2) -> (h2.getDeath() - h2.getBirth()) - (h1.getDeath() - h1.getBirth())).limit(3).forEach(h -> System.out.println(h.getName()));
        /// 6. 按各个年龄段分组： 0~20, 21~40, 41~60, 60以上
        Map<String, List<Heroes>> g2 = list.stream().collect(Collectors.groupingBy(h -> ageRange(h.getDeath() - h.getBirth())));
        g2.forEach((k, v) -> { System.out.println(k + ":" + v.stream().map(heroes -> heroes.getName()).collect(Collectors.toList()));
        });
        // 7. 按武力段分组： >=90， 80~89, 70~79, <70
        Map<String, List<Heroes>> group3 = list.stream().collect(Collectors.groupingBy(h -> powerRange(h.getPower())));
        group3.forEach((k, v) -> { System.out.println(k + ":" + v.stream().map(heroes -> heroes.getName()).collect(Collectors.toList()));
        });
        //8.按出生地分组后，统计各组人数
        Map<String, Long> g4 = list.stream().collect(Collectors.groupingBy((h) -> h.getLoc(), Collectors.counting()));
        g4.forEach((k, v) -> { System.out.println(k + ":" + v);
        });
    }

    public static String ageRange(int age) {
        if (age >= 0 && age <= 20) {
            return "0~20";
        } else if (age >= 21 && age <= 40) {
            return "21~40";
        } else if (age >= 41 && age <= 60) {
            return "41~60";
        } else {
            return "60以上";
        }
    }
    public static String powerRange(int power) {
        if (power >= 90) {
            return "90以上";
        } else if (power >= 80 && power <= 89) {
            return "80~89";
        } else if (power >= 70 && power <= 79) {
            return "70~79";
        } else {
            return "70之下";
        }
    }
    }

