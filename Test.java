import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 
 * IIN example:
 *
 * 1 2      3 4     5 6     |   7 8 9 0 1           |   2
 * ----------------------------------------------------------------
 * year     month   day     |   identified number   |   check sum
 * 
 */

public class Example1 {

    private String iin;
    private int year;
    private int month;
    private int day;
    private boolean sex = false;

    public static void main(String[] args)
    {
        Example1 hw = new Example1();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            hw.iin = reader.readLine(); // Getting IIN
            Long.parseLong(hw.iin); // Checking if input is digits

            // Checking length
            if (hw.iin.length() != 12) {
                throw new IllegalArgumentException("Incorrect IIN length!");
            }

            // checking sums
            if (hw.checkSum(hw.iin)) {
                hw.checkBirth(hw.iin);
                System.out.println("Correct IIN!");
                System.out.println("Date of birth: " + hw.day + "." + hw.month + "." + hw.year);
                System.out.println("Gender: " + (hw.sex ? "Woman" : "Man"));

            } else {
                System.out.println("Umm... Seems like it's not correct IIN, but maybe operators made mistake");
            }

        } catch (NumberFormatException e) {
            System.out.println("IIN can contain only digits!");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void checkBirth(String s) {

        year = Integer.parseInt(charToStr(s.charAt(0)) + charToStr(s.charAt(1)));   // year of birth
        month = Integer.parseInt(charToStr(s.charAt(2)) + charToStr(s.charAt(3)));  // month of birth
        day = Integer.parseInt(charToStr(s.charAt(4)) + charToStr(s.charAt(5)));    // day of birth

        // checking month and day of birth
        if (month < 0 || month > 12) {
            throw new IncorrectDataException("Incorrect month!");
        }
        if (day < 0 || day > 31) {
            throw new IncorrectDataException("Incorrect year!");
        }

        switch (charToStr(s.charAt(6))) {
            case "1":
                year += 1800;
                sex = false;
                break;
            case "2":
                year += 1800;
                sex = true;
                break;
            case "3":
                year += 1900;
                sex = false;
                break;
            case "4":
                year = 1900;
                sex = true;
                break;
            case "5":
                year = 2000;
                sex = false;
                break;
            case "6":
                year = 2000;
                sex = true;
                break;
            default:
                throw new IncorrectDataException("Incorrect birth century/gender!");
        }
    }

    private String charToStr(char c) {
        return String.valueOf(c);
    }

    private int charToInt(char c) {
        return Integer.parseInt(String.valueOf(c));
    }

    private boolean checkSum(String s) {

        int sum = 0;
        int module;

        // first step.
        for (int i = 0; i < s.length() - 1; i++) {
            sum += charToInt(s.charAt(i)) * (i + 1);
        }

        module = sum % 11;

        if (module == 10) {
            
            
            sum = 0;
            module = 0;
            int[] control = {3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2};
            int k;


            for (int i = 0; i < s.length()-1; i++) {
                k = control[i] % 11;
                if (k == 0) {
                    k = 11;
                }
                sum += charToInt(s.charAt(i)) * k;

            }

            module = sum % 11;

            if (module == 10) {
                return false;
            } else {
                if (module == charToInt(s.charAt(11))) {
                    return true;
                }
            }

        } else {
            if (module == charToInt(s.charAt(11))) {
                return true;
            }
        }

        return true;
    }
}

class IncorrectDataException extends IllegalArgumentException {
    public IncorrectDataException(String s) {
        System.err.println(s);
    }
}
