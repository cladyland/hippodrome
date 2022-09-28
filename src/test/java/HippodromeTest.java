import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    private Hippodrome hippodrome;
    private List<Horse> horses;
    private Throwable exception;

    @Test
    void firstConstructorArgIsNull() {
        exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Hippodrome(null);
                });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructorArgIsBlank(){
        List<Horse> horses = new ArrayList<>();
        exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Hippodrome(horses);
                });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesReturnTheSameList(){
        List<Horse> thirtyHorses = get30Horses();
        hippodrome = new Hippodrome(thirtyHorses);
        assertEquals(thirtyHorses, hippodrome.getHorses());
    }

    @Test
    void moveVerify(){
        new Hippodrome(get50Horses()).move();
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner(){
        Horse horse1 = new Horse("qwe1", 2, 2.4);
        Horse horse2 = new Horse("qwe2", 2.1, 3);
        Horse horse3 = new Horse("qwe3", 2.2, 2.2);
        Horse horse4 = new Horse("qwe4", 1.9, 2.1);
        hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse2, hippodrome.getWinner());
    }

    private List<Horse> get30Horses(){
        horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("" + i, i, i));
        }
        return horses;
    }

    private List<Horse> get50Horses(){
        horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        return horses;
    }

}