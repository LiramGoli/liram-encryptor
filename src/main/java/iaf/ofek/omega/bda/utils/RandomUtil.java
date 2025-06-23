package iaf.ofek.omega.bda.utils;

import java.util.Random;

public class RandomUtil {

    Random rand;

    public RandomUtil(Random rand) {
        this.rand = rand;
    }

    public Integer generateRandomInteger(Integer maxValue) {
        return rand.nextInt(maxValue);
    }

}
