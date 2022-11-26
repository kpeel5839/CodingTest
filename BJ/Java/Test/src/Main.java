import java.util.*;
import java.io.*;

public class Main {
    public class Parent {
        public void inheritance() {
            System.out.println("부모 클래스 메소드");
        }
    }

    public class Child extends Parent {
        public void main(String[] args) {
            Child child = new Child();
            child.inheritance();
        }
    }
}