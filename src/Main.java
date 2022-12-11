public class Main {
    public static void main(String[] args) {
//        Field field = Field.parse("""
//                Щ - - * * - - - - -
//                - - - - * - * * - -
//                - - - * - * - - - *
//                - * - - - - - - Ч -
//                - - - - - - * - - -
//                - - * - - * - - - -
//                - - - * - - * * * -
//                - - - - - - - * - -
//                - - - - - - - * - -
//                - - - - - * * - - -""");

        Field field = Field.parse("""
                Щ - - * * - - - - -
                - - - - * - * * - -
                - - - * - * - - - *
                - * - - - - - - - -
                - - - - - - * - - -
                - - * - - * - - - -
                - - - * - - * * * -
                - - - - - - - * - -
                - - - - - - - * - -
                - - - - - * * - - Ч""");

        field.print();

        // краткое решение:
//        System.out.println(field.solution());

        // словообильное решение:
        field.find_and_print();

        field.printAccessibilityMask();

    }


}