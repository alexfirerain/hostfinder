import java.util.ArrayList;
import java.util.List;

public class Field {
    char[][] cells;
    int ownerX = -1, ownerY = -1;

    public Field(char[][] cells) {
        this.cells = cells;
    }

    public Field(char[][] cells, int ownerX, int ownerY) {
        this.cells = cells;
        this.ownerX = ownerX;
        this.ownerY = ownerY;
    }

    public static Field parse(String field) {
        int x = -1;
        int y = -1;
        List<char[]> rows = new ArrayList<>();
        String[] lines = field.split("\n");

        if (lines.length < 1 || lines[0].isBlank())
            throw new IllegalArgumentException("Поле не найдено!");
        if (!lines[0].startsWith("Щ"))
            throw new IllegalArgumentException("В начале этого поля нет щенка!");

        for (int i = 0; i < lines.length; i++) {
            String s = lines[i];
            if (s.indexOf('Ч') > -1) {
                if (x != -1)
                    throw new IllegalArgumentException("На этом поле более одного хозяина!");
                x = i;
                y = s.indexOf('Ч');
            }
            rows.add(s.toCharArray());
        }

        if (x == -1)
            throw new IllegalArgumentException("На этом поле нет хозяина!");

        return new Field(rows.toArray(new char[0][]), x, y);
    }

    public void print() {
        System.out.println("Кактусовое поле, на котором щенок ищет хозяина:");
        StringBuilder sb = new StringBuilder();
        for (char[] line : cells) {
            for (char c : line) sb.append(c);
            sb.append('\n');
        }
        System.out.print(sb);
        System.out.printf("Хозяин находится в координатах %s:%s", ownerX, ownerY);
    }

    public boolean isReachableFromLeft(int x, int y) {
        return x > 0 && cells[x - 1][y] != '*';
    }
    public boolean isReachableFromAbove(int x,  int y) {
        return y > 0 && cells[x][y - 1] != '*';
    }

    public char checkCellReachability(int x, int y) {
        return isReachableFromLeft(x, y) ?
                'L' :
                 isReachableFromAbove(x, y) ?
                    'U' :
                         'N';
    }

    public void printAccessibilityMask() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++)
                sb.append(
                    cells[i][j] == '-' ?
                        checkCellReachability(i, j) :
                            cells[i][j]
                );
            sb.append("\n");
        }
        System.out.print(sb);
    }

}
