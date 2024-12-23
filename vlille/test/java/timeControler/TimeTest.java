package timeControler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {

    private Time time;

    @BeforeEach
    void setUp() {
        this.time = new Time();
    }

    @Test
    void testTimeAdded(){
        int act = this.time.getInterValeNoModif();
        this.time.addOneInterValeNoModif();
        assertEquals(1,this.time.getInterValeNoModif()-act);
    }

    @Test
    void testTimeReset(){
        this.time.addOneInterValeNoModif();
        assertEquals(1,this.time.getInterValeNoModif());
        this.time.resetCount();
        assertEquals(0,this.time.getInterValeNoModif());
    }


    @Test
    void testPredicatTime(){
        this.time.addOneInterValeNoModif();
        assertEquals(1,this.time.getInterValeNoModif());
        assertTrue(this.time.intervalNoModifSupEqHas(1));
        assertFalse(this.time.intervalNoModifSupEqHas(2));
    }
}