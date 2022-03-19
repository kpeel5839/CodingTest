public class Reservation {
    //private 인스턴스 변수
    private int id;          //예약번호
    private int startTime;   //시작시간
    private int endTime;     //종료시간

    //예약 번호, 시작시간, 종료시간을 매개변수로 받아 초기화 하는 생성자
    public Reservation(int id, int startTime, int endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getLength() {
        return endTime - startTime;
    }

    public String toString() {
        return id + " " + startTime + "시~" + endTime + "시 " + getLength() + "시간";
    }

    //Getter
    public int getId() {
        return id;
    }
    public int getStartTime() {
        return startTime;
    }
    public int getEndTime() {
        return endTime;
    }
}