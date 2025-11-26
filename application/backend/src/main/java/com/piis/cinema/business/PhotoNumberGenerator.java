package com.piis.cinema.business;

import java.time.OffsetDateTime;
import java.util.Random;

public class PhotoNumberGenerator {
    public static String generatePhotoNumber(OffsetDateTime when) {
        return "P%s%s%s%s%s%s%s.png".formatted(
                when.getMonth().ordinal(),
                when.getYear(),
                when.getHour(),
                when.getDayOfMonth(),
                when.getSecond(),
                when.getMinute(),
                randomInt(10, 100)
        );
    }

    private static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

}