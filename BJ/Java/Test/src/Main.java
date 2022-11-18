import java.awt.geom.AffineTransform;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static final String UP = "U";
    static final String DOWN = "D";

    static class GameHost {
        List<String> bridge;
        int playerIndex;
        boolean alive;

        GameHost(List<String> bridge, int playerIndex, boolean alive) {
            this.bridge = bridge;
            this.playerIndex = playerIndex;
            this.alive = alive;
        }

        public String stepOfIndexInBridge(int index) {
            return this.bridge.get(index);
        }

        public int getPlayerIndex() {
            return this.playerIndex;
        }

        public boolean playerAlive() {
            return this.alive;
        }
    }

    public static void printMap(GameHost gameHost) { // 여기서 player 위치는 index 로 따졌을 때 현재 위치인 것
        System.out.println(getPrintOfMap(gameHost, UP));
        System.out.println(getPrintOfMap(gameHost, DOWN));
    }

    private static StringBuilder getPrintOfMap(GameHost gameHost, String position) {
        StringBuilder printOfMap = new StringBuilder();
        appendPastMap(gameHost, printOfMap, position);
        appendNowMap(gameHost, printOfMap, position);
        translateLastLetter(printOfMap);
        return printOfMap;
    }

    private static void appendPastMap(GameHost gameHost, StringBuilder printOfMap, String position) {
        for (int index = 0; index < gameHost.getPlayerIndex(); index++) {
            if (gameHost.stepOfIndexInBridge(index).equals(position)) {
                printOfMap.append("| ").append("O ");
            }

            if (!gameHost.stepOfIndexInBridge(index).equals(position)) {
                printOfMap.append("| ").append("  ");
            }
        }
    }

    private static void appendNowMap(GameHost gameHost, StringBuilder printOfMap,String position) {
        if (gameHost.playerAlive()) { // 플레이어가 살아있을 떄
            appendNowMapWhenAlive(gameHost, printOfMap, position);
        }

        if (!gameHost.playerAlive()) { // 플레이어가 죽었을 때
            appendNowMapWhenNotAlive(gameHost, printOfMap, position);
        }
    }

    private static void appendNowMapWhenAlive(GameHost gameHost, StringBuilder printOfMap, String position) {
        if (gameHost.stepOfIndexInBridge(gameHost.getPlayerIndex()).equals(position)) {
            printOfMap.append("| ").append("O ");
        }

        if (!gameHost.stepOfIndexInBridge(gameHost.getPlayerIndex()).equals(position)) {
            printOfMap.append("| ").append("  ");
        }
    }

    private static void appendNowMapWhenNotAlive(GameHost gameHost, StringBuilder printOfMap, String position) {
        if (gameHost.stepOfIndexInBridge(gameHost.getPlayerIndex()).equals(position)) {
            printOfMap.append("| ").append("  ");
        }

        if (!gameHost.stepOfIndexInBridge(gameHost.getPlayerIndex()).equals(position)) {
            printOfMap.append("| ").append("X ");
        }
    }

    private static void translateLastLetter(StringBuilder printOfMap) {
        printOfMap.setCharAt(0, '[');
        printOfMap.append("]");
    }

    public static void main(String[] args) {
        List<String> map = List.of("U", "D", "D", "U");

        GameHost gameHost = new GameHost(map, 0, false);
        printMap(gameHost);
    }
}
