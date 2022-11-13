import java.util.*;
import java.io.*;

// 1620 : 나는야 포켓몬 마스터 이다솜

/**
 * 예제
 * 26 5
 * Bulbasaur
 * Ivysaur
 * Venusaur
 * Charmander
 * Charmeleon
 * Charizard
 * Squirtle
 * Wartortle
 * Blastoise
 * Caterpie
 * Metapod
 * Butterfree
 * Weedle
 * Kakuna
 * Beedrill
 * Pidgey
 * Pidgeotto
 * Pidgeot
 * Rattata
 * Raticate
 * Spearow
 * Fearow
 * Ekans
 * Arbok
 * Pikachu
 * Raichu
 * 25
 * Raichu
 * 3
 * Pidgey
 * Kakuna
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1620_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        Map<String, Integer> mapOfIndex = new HashMap<>();
        Map<Integer, String> mapOfName = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            String pokemonName = br.readLine();
            mapOfIndex.put(pokemonName, i);
            mapOfName.put(i, pokemonName);
        }

        for (int i = 0; i < M; i++) {
            String query = br.readLine();
            char firstLetter = query.charAt(0);

            if ('1' <= firstLetter && firstLetter <= '9') { // 사실 0 은 들어오지 않기 떄문에 첫번쨰 자리에 과감히 없앰
                bw.write(mapOfName.get(Integer.parseInt(query)) + "\n");
            } else {
                bw.write(mapOfIndex.get(query) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
