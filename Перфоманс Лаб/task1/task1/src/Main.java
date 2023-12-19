import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        try (Scanner scanner = new Scanner(System.in);) {
            while (true) {
                int n = scanner.nextInt(), m = scanner.nextInt();

                List<Integer> temporal = printResult(n, m);

                temporal.forEach(System.out::print);
                System.out.println();
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Введите первое число которое будет больше 0");
        }
    }

    private static List<Integer> printResult(int lengthArray, int interval) {
        if (lengthArray > 0) {

            List<Integer> result = new ArrayList<>();
            int[] array = new int[lengthArray];
            for (int i = 0; i < array.length; i++) {
                array[i] = i + 1;
            }
            int firstValue = array[0];
            int currentValue;
            int counter = 0;
            result.add(firstValue);

            for (int i = 0; i < array.length; i++) {
                counter++;
                currentValue = array[i];
                if (counter == interval) {
                    counter = 0;
                    if (firstValue == currentValue) {
                        break;
                    }
                    result.add(currentValue);
                    counter++;
                }
                if (i == array.length - 1) {
                    i = -1;
                }
            }
            return result;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
}