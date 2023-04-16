import javax.swing.event.DocumentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// 11462 : The Owl and the Fox

public class Main {

    public static void main(String[] args) throws IOException {
        Map<Character, Integer> m = Map.of(
                'k', 2,
                's', 1
        );

        Map<Character, Integer> m2 = Map.of(
                'k', 2,
                's', 1
        );

        System.out.println(m.equals(m2));
    }
}

