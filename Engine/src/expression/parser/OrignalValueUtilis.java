package expression.parser;

import expression.api.Expression;
import expression.impl.BooleanExpression;
import expression.impl.Number;
import expression.impl.RawString;
import operation.Operation;
import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class OrignalValueUtilis {


    private static boolean isBoolean(String value) {
        return "true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value);
    }

    private static boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private static Expression primitiveParseToExpression(String inputToCell) {

        if (isBoolean(inputToCell)) {
            return new BooleanExpression(Boolean.parseBoolean(inputToCell));
        } else if (isNumeric(inputToCell)) {
            return new Number(Double.parseDouble(inputToCell));
        } else {
            return new RawString(inputToCell);
        }

    }


    public static Expression toExpression(String input) {
        List<String> parts = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        if (input.startsWith("{") && input.endsWith("}")) {

            String functionContent = input.substring(1, input.length() - 1);

            for (char c : functionContent.toCharArray()) {
                if (c == '{') {
                    stack.push(c);
                } else if (c == '}') {
                    stack.pop();
                }

                if (c == ',' && stack.isEmpty()) {
                    // If we are at a comma and the stack is empty, it's a separator for top-level parts
                    parts.add(buffer.toString().trim());
                    buffer.setLength(0); // Clear the buffer for the next part
                } else {
                    buffer.append(c);
                }
            }

            // Add the last part
            if (buffer.length() > 0) {
                parts.add(buffer.toString().trim());
            }

            String functionName = parts.get(0).trim().toUpperCase();
            parts.removeFirst();

            Stream<Object> listOfArg = Stream.of(parts.toArray()).map(argument ->  toExpression((String) argument));

           return Operation.valueOf(functionName).create(listOfArg.toArray());

        } else {
            //number, string, boolean
            return primitiveParseToExpression(input);
        }

    }

    public static Set<String> findInfluenceFrom(String value)
    {
        Set<String> cellDependence = new HashSet<>();

        // Define the regex pattern to match the structure and capture the value after the comma
        Pattern pattern = Pattern.compile("\\{REF,\\s*([A-Z]\\d+)\\}");

        // Create a matcher for the input string
        Matcher matcher = pattern.matcher(value);

        // Find all matches in the string
        while (matcher.find()) {
            String extractedValue = matcher.group(1);
            cellDependence.add(extractedValue);
        }
        return cellDependence;
    }

}
