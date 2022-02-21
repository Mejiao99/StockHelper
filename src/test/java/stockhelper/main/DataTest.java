package stockhelper.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {
    @Test
    public void foo() {
        Data data = new Data();
        data.setFoo(5);
        System.err.println(data.getFoo());
    }
}