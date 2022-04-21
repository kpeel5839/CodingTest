import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{

        // 파일 생성 주기
        int diff = 3000;

        // index == targetIndex 이면 실행
        int targetIndex = diff / 100; // targetIndex is 700

        int index = targetIndex;

        // 이전 시각과 현재 시각이 비교했을 때 , diff 의 차이가 있어야함
        while (true) {

            Date now = new Date();
            String format = "yyyy-MM-dd HH.mm";
            DateFormat df = new SimpleDateFormat(format);
            String fileName = df.format(now);

            // 폴더 새로 생성
            File dir = new File("C:/Temp/" + fileName);
            if (dir.exists() == false) {
                dir.mkdirs();
            }

            // targetIndex 보다 index 가 작으면 실행이 된다 , targetIndex == index 인 순간에 실행이 되는 것이다.
            if(index < targetIndex) Thread.sleep(100);

            // 1. 폴더 만들기
            // 날짜로 폴더이름
            if(targetIndex != index++) continue;

            index = 0;
            System.out.println(LocalDateTime.now().getSecond());

            // 2. 파일 복사
            // 파일 이름 랜덤으로 바꿔줌.
            UUID uuid = UUID.randomUUID();

            String sourceFileName = "C:/Temp/source.zip";
            String targetFileName = "C:/Temp/" + fileName + "/" + uuid + ".zip";

            FileInputStream fis = new FileInputStream(sourceFileName);
            FileOutputStream fos = new FileOutputStream(targetFileName);

            int readByteNo;

            byte[] readBytes = new byte[40];

            while ((readByteNo = fis.read(readBytes)) != -1) {
                fos.write(readBytes, 0, readByteNo);

            } // while

            fos.flush();
            fos.close();
            fis.close();

        } // while
    }
}