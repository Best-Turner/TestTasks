import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyList<String> myList = new MyArrayList<>();

        myList.add("Hello");
        myList.add("World");
        myList.add("Assalam");


        String command1 = myList.get(0);
        String command2 = myList.get(1);
        String command3 = myList.get(2);

        System.out.println(myList.size());

//        System.out.println(command1);
//        System.out.println(command2);
//        System.out.println(command3);

        myList.remove("Hello");
        String command4 = myList.get(0);
        System.out.println(command4);
        System.out.println(myList.size());

        for (int i = 0; i < 20; i++) {
            myList.add("Line " + i);
        }
        System.out.println("до удаления " + myList.size());

        myList.remove("Line " + 10);

        System.out.println("После удаления "  + myList.size());



    }
}