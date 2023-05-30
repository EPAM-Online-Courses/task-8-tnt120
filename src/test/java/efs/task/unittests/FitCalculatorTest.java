package efs.task.unittests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietNotRecommended() {
        double weight = 69.5;
        double height = 1.72;

        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertFalse(recommended);
    }

    @Test
    void shouldThrowIllegalArgumentException_whenHeightIsZero() {
        double weight = 69.5;
        double height = 0.0;

        assertThrows(IllegalArgumentException.class, () -> {
            FitCalculator.isBMICorrect(weight, height);
        });
    }

    @ParameterizedTest(name = "weight={0}, height={1}")
    @ValueSource(doubles = {99.4, 89.3, 91.7})
    void shouldReturnTrue_forAllWeights(double weight) {
        double height = 1.84;

        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertTrue(recommended);
    }

    @ParameterizedTest(name = "weight={0}, height={1}")
    @CsvSource({
            "71.3, 1.84",
            "66.7, 1.78",
            "92.1, 2.01"
    })
    void shouldReturnFalse_forAllWeightsAndHeights(double weight, double height) {

        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertFalse(recommended);
    }

    @ParameterizedTest(name = "weight={0}, height={1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_forAllWeightsAndHeightsFromCsvFile(double weight, double height) {

        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithWorstBMI_whenTestUserListProvided() {
        // Given
        double expectedWeight = 97.3;
        double expectedHeight = 1.79;

        // When
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(TestConstants.TEST_USERS_LIST);

        // Then
        assertNotNull(userWithWorstBMI);
        Assertions.assertEquals(expectedWeight, userWithWorstBMI.getWeight());
        Assertions.assertEquals(expectedHeight, userWithWorstBMI.getHeight());
    }

    @Test
    void shouldReturnNull_whenUserListEmptyProvided() {
        List<User> emptyUserList = Collections.emptyList();

        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(emptyUserList);

        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnTestUserBMIScore_whenTestUserListProvided() {
        List<User> usersList = TestConstants.TEST_USERS_LIST;
        double[] expectedScores = TestConstants.TEST_USERS_BMI_SCORE;

        double[] actualScores = FitCalculator.calculateBMIScore(usersList);

        Assertions.assertArrayEquals(expectedScores, actualScores);
    }
}