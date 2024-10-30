package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlateNumberHelper {
    private static final String NUMBER_PLATE_PATTERN = "[A-Z]{2}[0-9]{2}\\s?[A-Z]{3}";

    public List<String> find(String text) {
        Matcher matcher = getMatcher(text);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.toMatchResult().group().replace(" ", ""));
        }
        return result;
    }

    private Matcher getMatcher(String text) {
        return Pattern.compile(NUMBER_PLATE_PATTERN).matcher(text);
    }
}
