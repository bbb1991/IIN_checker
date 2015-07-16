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

    public static void main(String[] args) {

        IinGenerator generator = new IinGenerator();

        for (int i = 0; i < 100; i++) {
            generator.generateDate();
            System.out.println(generator.sb);
            generator.sb.delete(0, generator.sb.length());
        }
    }

    // First step: IIN must have date of birth:
    // For example: if birth date 15 june 1991
    // it looks like 910615
    // IIN
    private void generateDate() {

        // About month age I found this awesome class. Don't know how I lived without it before
        Random r = new Random();
        int year, month, day;

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

        // Ok. Now we need day. But... We have 31 day of month, 30 also 28 and... 29
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
                    if (day > 0)
                    {
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

        numbCorrect(year);
        numbCorrect(month);
        numbCorrect(day);
    }

    // I need this method. Because if will be generated number between 0..9
    // we getting 3 .. 5 numbers instead of 6. for example: "01 december, 2001" will be "1201"
    // but we wanted "012001"
    private void numbCorrect(int i) {
        if (i < 10) {
            sb.append("0").append(String.valueOf(i));
        } else {
            sb.append(i);
        }
    }
}