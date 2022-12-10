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
                - - - * - Ч * * * -
                - - - - - - - * - -
                - - - - - - - * - -
                - - - - - * * - - -""");

        field.print();
        field.printAccessibilityMask();
        System.out.println(field.solution());

    }


}