import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Приложение для случайной генераций ИИН.
 * Понадобилось для работы, так как оказывается придумать корректный ИИН куда труднее
 */


public class IinGenerator {

    // Process generating will be in several steps (still not know how many)
    // And I think StringBuffer is much better idea than String or long
    private StringBuffer sb = new StringBuffer(12);

    // About month age I found this awesome class. Don't know how I lived without it before
    Random r = new Random();

    public static void main(String[] args) {

        IinGenerator g = new IinGenerator();

        g.generateDate();
        g.generateSerialNumber();
        g.calculateControlNumber();
        System.out.println(g.sb);


    }

    // First step: IIN must have date of birth:
    // For example: if birth date 15 june 1991
    // it looks like 910615
    // IIN
    private void generateDate() {

        GregorianCalendar calendar = new GregorianCalendar();
        int year = r.nextInt(215) + 1800; // generating year of birth between 1800 .. 2014

        int dayOfYear = r.nextInt(365) + 1;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int century_gender = Integer.parseInt(String.valueOf(year).substring(0, 2));
        year = Integer.parseInt(String.valueOf(year).substring(2));

        // Generating gender and century.
        // if RANDOM % 2 == 0 this is woman/girl/grandma/..
        // if RANDOM % 2 != 0 this is boy/men/granpa/..
        // 1, 2 - 1800
        // 3, 4 - 1900
        // 5, 6 - 2000

        // if year generated between 1800 .. 1899
        if (century_gender == 18) {
            century_gender = r.nextInt(3) + 1;
        } else if (century_gender == 19) { // if year between 1900 .. 1999
            century_gender = r.nextInt(3) + 2;
        } else { // and 2000 .. 2014
            century_gender = r.nextInt(3) + 4;
        }

        numbCorrect(year);
        numbCorrect(month);
        numbCorrect(day);
        sb.append(century_gender); // Not need check
    }

    // I need this method. Because if will be generated number between 0..9
    // we may getting 3 .. 5 numbers instead of 6. for example: "01 december, 2001" will be "1201"
    // but we wanted "012001"
    private void numbCorrect(int i) {
        if (i < 10) {
            sb.append("0").append(String.valueOf(i));
        } else {
            sb.append(i);
        }
    }

    // next step - serialNumber.
    // xxxxxxxNNNNx
    // where N is serial number. 4 digit
    private void generateSerialNumber() {
        int serialNumber = r.nextInt(10000);
        sb.append(String.format("%04d", serialNumber));
    }

    // Step three. Calculating of control number.
    private void calculateControlNumber() {
        String s = sb.toString();
        long control = 0;

        System.out.println("1");

        for (int i = 0; i < 11; i++) {
            control += Integer.parseInt(String.valueOf(s.charAt(i))) * i+1;
        }

        control %= 11;

        if (control == 10) {
            System.out.println("2");
            int[] k  = {3, 4, 5, 6, 7, 8, 9, 10, 11, 1, 2};
            control = 0;

            for (int i = 0; i < 11; i++) {
                control += Integer.parseInt(String.valueOf(s.charAt(i))) * k[i];
            }

            assert control != 10;
        }
        sb.append(control);
    }
}