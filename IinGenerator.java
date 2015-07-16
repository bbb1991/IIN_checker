import java.util.Random;

/**
 * Приложение для случайной генераций ИИН.
 * Понадобилось для работы, так как оказывается придумать корректный ИИН куда труднее
 *
 */


public class IinGenerator {

    // Process generating will be in several steps (still not know how many)
    // And I think StringBuffer is much better idea than String or long
    private StringBuffer sb = new StringBuffer(12);

    // About month age I found this awesome class. Don't know how I lived without it before
    Random r = new Random();

    public static void main(String[] args) {

        IinGenerator generator = new IinGenerator();

        generator.generateDate();
        generator.generateSerialNumber();
        generator.calculateControlNumber();
        System.out.println(generator.sb);


    }

    // First step: IIN must have date of birth:
    // For example: if birth date 15 june 1991
    // it looks like 910615
    // IIN
    private void generateDate() {


        int year, month, day, century_gender;

        // Generate year. Must be between 00 (like 2000 or 1900 or even 1800 why not?!)
        // and 99 (1999, 1899 ...)
        year = r.nextInt(99);

        // Now we generating month. Must be between 01 (January) and 12 (December)
        while (true) {
            month = r.nextInt(13);
            if (month > 0) {
                break;
            }
        }

        // Ok. Now we need day. But... We have 31 day of month, 30 also 28 and... oh dear, 29
        boolean isDayCorrect = false;
        do {
            day = r.nextInt(32);
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (day > 0) {
                        isDayCorrect = true;
                    }
                    break;
                // TODO fix february month. Let me sleep on it
                case 2:
                case 4:
                case 6:
                case 9:
                case 11:
                    if (day > 0 && day < 31) {
                        isDayCorrect = true;
                    }
                    break;
            }
        } while (!isDayCorrect);

        // Generating gender and century.
        // if RANDOM % 2 == 0 this is woman/girl/grandma/..
        // if RANDOM % 2 != 0 this is boy/men/granpa/..
        // 1, 2 - 1800
        // 3, 4 - 1900
        // 5, 6 - 2000
        while (true) {
            century_gender = r.nextInt(7);
            if (century_gender > 0) {
                break;
            }
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

        for (int i = 0; i < 11; i++) {
            control += Integer.parseInt(String.valueOf(s.charAt(i))) * i+1;
        }

        control %= 11;

        if (control == 10) {
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