import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {
    private final String name = "Horse";
    private final double speed = 2.1;
    private final double distance = 1;
    private final Horse horse = new Horse(name, speed, distance);
    private final double min = 0.2;
    private final double max = 0.9;
    private Throwable exception;

    @Test
    void firstConstructorArgIsNull() {
        exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(null, 0);
                });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t\t\t", "\n\n\n\n"})
    void firstConstructorArgIsBlank(String argument) {
        exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(argument, 0);
                });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void secondConstructorArgIsNegative() {
        exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(name, -1);
                });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void thirdConstructorArgIsNegative(){
        exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(name, 1, -1);
                });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameReturnFirstConstructorArg() throws NoSuchFieldException, IllegalAccessException {
        Field horseName = Horse.class.getDeclaredField("name");
        horseName.setAccessible(true);
        assertEquals(name, horseName.get(horse));
    }

    @Test
    void getSpeedReturnSecondConstructorArg(){
        assertEquals(speed, horse.getSpeed());
    }

    @Test
    void getDistanceReturnThirdConstructorArg(){
        assertEquals(distance, horse.getDistance());
        assertEquals(0, (new Horse(name, speed)).getDistance());
    }

    @Test
    void whenMoveCallsGetRandomDouble(){
        try (MockedStatic<Horse> mock = Mockito.mockStatic(Horse.class)) {
            horse.move();
            mock.verify(() -> Horse.getRandomDouble(min, max));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.4, 0.5, 0.6, 0.7})
    void moveAssignDistanceValue(Double randomDouble){
        double expected = speed * randomDouble;
        try (MockedStatic<Horse> mock = Mockito.mockStatic(Horse.class)) {
            mock.when(() -> Horse.getRandomDouble(min, max)).thenReturn(randomDouble);
            double actual = speed * Horse.getRandomDouble(min, max);
            assertEquals(expected, actual);
        }
    }

}