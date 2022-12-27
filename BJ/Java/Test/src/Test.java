import java.util.Scanner;
import java.util.function.Supplier;

public class Test {

    private int a;
    private int b;

    public Test(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
    }

    public static class Builder {
        private int a;
        private int b;

        public Builder() {
        }

        public Builder a(int a) {
            this.a = a;
            return this;
        }

        public Builder b(int b) {
            this.b = b;
            return this;
        }

        public Test build() {
            return new Test(this);
        }

    }

    @Override
    public String toString() {
        return a + " " + b;
    }
}
