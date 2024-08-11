package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {

    private final int rows, columns;
    final MazeLocation start;
    private final MazeLocation end;
    private Cell[][] grid;

    public Maze(int rows, int columns, MazeLocation start, MazeLocation end, double sparseness) {
        // инициализация базовых переменных экземпляра
        this.rows = rows;
        this.columns = columns;
        this.start = start;
        this.end = end;

        // заполнение сетки пустыми ячейками
        grid = new Cell[rows][columns];
        for (Cell[] row : grid) {
            Arrays.fill(row, Cell.EMPTY);
        }

        // заполнение сетки заблокированными ячейками
        randomlyFill(sparseness);

        // заполнение начальной и конечной позиций в лабиринте
        grid[start.row][start.column] = Cell.START;
        grid[end.row][end.column] = Cell.GOAL;
    }

    public Maze() {
        this(10, 10, new MazeLocation(0, 0), new MazeLocation(9, 9), 0.2);
    }

    private void randomlyFill(double sparseness) {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (Math.random() < sparseness) {
                    grid[row][column] = Cell.BLOCKED;
                }
            }
        }
    }

    public List<MazeLocation> getSuccessors(MazeLocation ml) {
        List<MazeLocation> locations = new ArrayList<>();
        if (ml.row + 1 < rows && grid[ml.row + 1][ml.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(ml.row + 1, ml.column));
        }
        if (ml.row - 1 >= 0 && grid[ml.row - 1][ml.column] != Cell.BLOCKED) {
            locations.add(new MazeLocation(ml.row - 1, ml.column));
        }
        if (ml.column + 1 < columns && grid[ml.row][ml.column + 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(ml.row, ml.column + 1));
        }
        if (ml.column - 1 >= 0 && grid[ml.row][ml.column - 1] != Cell.BLOCKED) {
            locations.add(new MazeLocation(ml.row, ml.column - 1));
        }

        return locations;
    }

    public void mark(List<MazeLocation> path) {
        for (MazeLocation ml : path) {
            grid[ml.row][ml.column] = Cell.PATH;
        }
        grid[start.row][start.column] = Cell.START;
        grid[end.row][end.column] = Cell.GOAL;
    }

    public void clear(List<MazeLocation> path) {
        for (MazeLocation ml : path) {
            grid[ml.row][ml.column] = Cell.EMPTY;
        }
        grid[start.row][start.column] = Cell.START;
        grid[end.row][end.column] = Cell.GOAL;
    }

    // shortest way from a b point
    public double euclideanDistance(MazeLocation ml) {
        int xDist = ml.column - end.column;
        int yDist = ml.row - end.row;
        return Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
    }

    public double manhattanDistance(MazeLocation ml) {
        int xdist = Math.abs(ml.column - end.column);
        int ydist = Math.abs(ml.row - end.row);
        return (xdist + ydist);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                sb.append(cell.toString());
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public boolean isFinished(MazeLocation ml) {
        return end.equals(ml);
    }

    public enum Cell {
        EMPTY(" "),
        BLOCKED("X"),
        START("S"),
        GOAL("G"),
        PATH("*");

        private final String code;

        Cell(String c) {
            code = c;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public record MazeLocation(int row, int column) {}
}
