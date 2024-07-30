package tasks.search;

import org.junit.jupiter.api.Test;
import java.util.List;

class GenericSearchTest {

    Maze maze = new Maze();

    @Test
    void dfs() {
        GenericSearch.Node<Maze.MazeLocation> solution = GenericSearch.dfs(maze.start, maze::isFinished, maze::getSuccessors);
        if (solution == null) {
            System.out.println("No solution found using depth-first search!");
        } else {
            List<Maze.MazeLocation> path1 = GenericSearch.nodeToPath(solution);
            maze.mark(path1);
            System.out.println(maze);
            maze.clear(path1);
        }
    }

    @Test
    void bfs() {
        GenericSearch.Node<Maze.MazeLocation> solution = GenericSearch.bfs(maze.start, maze::isFinished, maze::getSuccessors);
        if (solution == null) {
            System.out.println("No solution found using depth-first search!");
        } else {
            List<Maze.MazeLocation> path2 = GenericSearch.nodeToPath(solution);
            maze.mark(path2);
            System.out.println(maze);
            maze.clear(path2);
        }
    }

    @Test
    void astar() {
        GenericSearch.Node<Maze.MazeLocation> solution3 = GenericSearch.astar(maze.start, maze::isFinished, maze::getSuccessors, maze::manhattanDistance);
        if (solution3 == null) {
            System.out.println("No solution found using A*!");
        } else {
            List<Maze.MazeLocation> path3 = GenericSearch.nodeToPath(solution3);
            maze.mark(path3);
            System.out.println(maze);
            maze.clear(path3);
        }
    }

}