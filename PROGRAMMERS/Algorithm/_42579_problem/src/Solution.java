import java.util.*;
import java.io.*;

class Solution {
    class Genres {
        int total;
        List<Song> songs = new ArrayList<>();

        Genres(int total, Song song) {
            this.total = total;
            this.songs.add(song);
        }
    }

    class Song {
        int index;
        int value;

        Song(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    public int[] solution(String[] genres, int[] plays) {
        List<Integer> res = new ArrayList<>();
        HashMap<String, Genres> map = new HashMap<>();

        // 일단 HashMap 을 만들어서, String, Genres 로 만든다.
        // 그리고 genres 와 plays 를 돌면서 Map 에다가 기록한다.
        // 이 때, 처음 오는 genres 이면, 즉 contains 하지 않으면, Genres 를 선언해주고 거기다가 현제 plays 를 넣어준다.
        // plays 를 넣어줄 때에도, 객체를 만들어서 넣어주어야 한다.
        // 객체를 만들때에는 현재 i 번과, plays[i] 값을 넣어준다.
        for (int i = 0; i < genres.length; i++) {
            Song song = new Song(i, plays[i]);

            if (map.containsKey(genres[i])) { // 만일 이미 존재한다라면?
                Genres g = map.get(genres[i]);
                g.total += plays[i];
                g.songs.add(song); // 사실 참조된 객체이기에 이렇게만 해도 될 것 같지만 혹시 모를 시간낭비를 위해 다시 넣어준다.
                map.put(genres[i], g);
            } else {
                map.put(genres[i], new Genres(plays[i], song)); // 처음이니 플레이 횟수와, 만든 song 객체를 삽입해준다.
            }
        }

        // KeySet 을 통해서, key 를 반복문으로 받아내고, Genres List 에다가 넣어준다.
        // 그리고 Genres 를 total 순으로 정렬한 뒤, 반복문을 순서대로 돌아준다.
        // 반복문을 돌면서, songs 를 정렬하는데, 이 떄 기준은 value 내림차순, value 가 같다라면 index 오름차순으로 정렬하면 된다.
        // 정렬한 뒤, size 가 무조건 1 이상이니, 반복문을 돌아주는데 따로 index 변수를 선언하여 res 에 담을 떄 마다 ++ 를 해주고 이것이 만일 2가 된다라면 다음 반복으로 넘어가준다.
        List<Genres> list = new ArrayList<>();

        for (String key : map.keySet()) {
            list.add(map.get(key));
        }

        Collections.sort(list, (o1, o2) -> o2.total - o1.total);

        for (Genres g : list) {
            List<Song> s = g.songs;

            Collections.sort(s, (o1, o2) -> {
                if (o1.value == o2.value) {
                    return o1.index - o2.index;
                }

                return o2.value - o1.value;
            });

            int index = 0;

            for (Song song : s) {
                res.add(song.index);
                index++;

                if (index == 2) {
                    break;
                }
            }
        }

        return res.stream().mapToInt(i -> i).toArray();
    }
}