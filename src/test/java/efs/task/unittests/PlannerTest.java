package efs.task.unittests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class PlannerTest {
    private Planner planner;

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest(name = "Activity Level = {0}")
    @EnumSource(ActivityLevel.class)
    void shouldCorrectlyCalculateDailyCaloriesDemandForAllActivityLevels(ActivityLevel activityLevel) {
        User user = TestConstants.TEST_USER;

        double calculatedCalories = planner.calculateDailyCaloriesDemand(user, activityLevel);
        double expectedCalories = TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel);

        assertEquals(expectedCalories, calculatedCalories);
    }

    @Test
    void shouldCorrectlyCalculateDailyIntake() {
        User user = TestConstants.TEST_USER;
        DailyIntake expectedDailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        DailyIntake calculateDailyIntake = planner.calculateDailyIntake(user);

        assertAll("Daily Intake",
                () -> assertEquals(expectedDailyIntake.getCalories(), calculateDailyIntake.getCalories(), "Incorrect calories"),
                () -> assertEquals(expectedDailyIntake.getProtein(), calculateDailyIntake.getProtein(), "Incorrect protein"),
                () -> assertEquals(expectedDailyIntake.getFat(), calculateDailyIntake.getFat(), "Incorrect fat"),
                () -> assertEquals(expectedDailyIntake.getCarbohydrate(), calculateDailyIntake.getCarbohydrate(), "Incorrect carbohydrate")
        );
    }

}
