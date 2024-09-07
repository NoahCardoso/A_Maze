import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeBinaryTree extends JPanel implements KeyListener {
    private static final int CELL_SIZE = 15;
    private static final int WIDTH = 35;
    private static final int HEIGHT = 35;
    private static final int[][] maze = new int[WIDTH][HEIGHT];
    private int playerX = 1;
    private int playerY = 1;

    public MazeBinaryTree() {
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        initializeMaze();
        generateMaze();
        trimMaze();
        solveMaze();
    }

    private void initializeMaze() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                maze[x][y] = 0;
            }
        }
    }
    private void generateMaze() {
		for (int height = 1; height < HEIGHT - 1; height += 2){
			for (int width = 1; width < WIDTH - 1; width += 2){
				maze[height][width] = 1;
				if ((int) (Math.random () * 2) + 1 == 1)
                    maze[width - 1] [height] = 1;
                else
					maze[width] [height - 1] = 1;
			}
		}
    }
    private void trimMaze(){
        for (int height = 0; height < HEIGHT; height += 1){
			for (int width = 0; width < WIDTH; width += 1){
				if (height == 0 || height == HEIGHT-1 || width == 0 || width == WIDTH-1)
                    maze[width][height] = 1;
			}
		}
    }
    
    private void solveMaze(){
        for (int height = 1; height < HEIGHT - 1; height += 1){
			for (int width = 1; width < WIDTH - 1; width += 1){
                int surroundingWalls = 0, x = width, y = height;
                if (maze[x][y] == 1){
                    do{
                        int nx = 0, ny = 0;
                        int surroundings[] = {0,0,0,0};
                        if (maze[x][y - 1] != 1)
                            surroundings[0] = 1;
                        else
                            ny--;
                        if (maze[x + 1][y] != 1)
                            surroundings[1] = 1;
                        else
                            nx++;
                        if (maze[x][y + 1] != 1)
                            surroundings[2] = 1;
                        else
                            ny++;
                        if (maze[x - 1][y] != 1)
                            surroundings[3] = 1;
                        else
                            nx--;
                        surroundingWalls = surroundings[0] + surroundings[1] + surroundings[2] + surroundings[3];
                        if (surroundingWalls == 3){
                            if (x + nx > 0 && x + nx < WIDTH - 1 && y + ny > 0 && y + ny < HEIGHT - 1){
                                if (maze[nx + x][ny + y] == 1){
                                    maze[x][y] = 2;
                                    x += nx;
                                    y += ny;
                                }
                                else{
                                    break;
                                }
                            }
                            else
                                break;
                        }
    
                    }while (surroundingWalls == 3);
                }
                
                
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (maze[x][y] == 1) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
                if (maze[x][y] == 2) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        g.setColor(Color.RED);
        g.fillRect(playerX * CELL_SIZE, playerY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && maze[playerX][playerY - 1] == 1) {
            playerY--;
        } else if (key == KeyEvent.VK_DOWN && maze[playerX][playerY + 1] == 1) {
            playerY++;
        } else if (key == KeyEvent.VK_LEFT && maze[playerX - 1][playerY] == 1) {
            playerX--;
        } else if (key == KeyEvent.VK_RIGHT && maze[playerX + 1][playerY] == 1) {
            playerX++;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Generator");
        MazeBinaryTree mazePanel = new MazeBinaryTree();
        frame.add(mazePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
