public class Main {
    public static void main(String[] args) {
        Field field = Field.parse("""
                Щ - - * * - - - - -
                - - - - * - * * - -
                - - - * - * - - - *
                - * - - - - - - Ч -
                - - - - - - * - - -
                - - * - - * - - - -
                - - - * - - * * * -
                - - - - - - - * - -
                - - - - - - - * - -
                - - - - - * * - - -""");

        field.print();
        field.printAccessibilityMask();
        field.find_and_print();

    }


}