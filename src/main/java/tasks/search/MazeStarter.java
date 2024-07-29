package tasks.search;

import java.util.List;

public class MazeStarter {

    public static void run() {
        Maze maze = new Maze();

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
}
