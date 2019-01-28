package esadrcanfer.us.alumno.autotesting.util;

import android.support.test.espresso.core.internal.deps.guava.base.Strings;
import java.util.Random;

public class RandomUtils {
    public static String DEFAULT_ALPHABET = "";

    static {
        DEFAULT_ALPHABET = "abcdefghijklmnñopqrstuvwxyz"
                .concat("abcdefghijklmnñopqrstuvwxyz".toUpperCase()).concat("012345789");
    }

    public static String randomText(Integer length) {
        return randomText(length, null);
    }

    public static String randomText(Integer length, String alphabet) {
        String _alphabet = Strings.isNullOrEmpty(alphabet) ? DEFAULT_ALPHABET : alphabet;
        StringBuilder result = new StringBuilder();
        int i = 1;
        Random rand = new Random();
        while (i < length) {
            Integer index = rand.nextInt(_alphabet.length() - 1);
            result.append(_alphabet.charAt(index));
            i++;
        }
        return result.toString();
    }
}
