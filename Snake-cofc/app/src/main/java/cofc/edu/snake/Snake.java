package cofc.edu.snake;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Bitmap;

import java.util.Random;

/*
 * Top left of the grid is (0,0), bottom right is (14, 20)
 * The head of the snake is first in the array list
*/

public class Snake {

    private int score;
    public int getScore() { return score; }

    private int rows;
    private int cols;
    private int dx;
    private int dy;

    public Point food;
    ArrayList<Point> snake;

    public Snake(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        snake = new ArrayList<Point>();
        snake.add(new Point(5, 2));
        snake.add(new Point(4, 2));
        snake.add(new Point(3, 2));
        snake.add(new Point(2, 2));

        dx = 1;
        dy = 0;
        score = 4;

        moveFood();
    }

    // Move returns false if the next move is not valid,
    // i.e, game over
    public boolean move() {
        Point head = snake.get(0);
        int newX = head.x + dx;
        int newY = head.y + dy;

        //Test to see if new point of snake equals boundaries
        if (newX > cols || newY > rows
            || newX < 0 || newY < 0) {
            return false;
        }

        //Test to see if new point is already in the snake array
        for (int i = 1; i < snake.size(); i++) {
            Point p = snake.get(i);

            if (newX == p.x && newY == p.y) {
                return false;
            }
        }

        snake.add(0, new Point(newX, newY));

        if (newX == food.x && newY == food.y) {
            score++;
            moveFood();
        } else {
            snake.remove(snake.size() - 1);
        }

        return true;
    }

    private void moveFood() {
        ArrayList<Point> gridList = new ArrayList<Point>();
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {
                boolean onSnake = false;
                for (int k = 0; k < snake.size(); k++) {
                    Point p = snake.get(k);
                    onSnake = onSnake || (p.x == j && p.y == i);
                }
                if (!onSnake) {
                    gridList.add(new Point(j, i));
                }

            }
        }
        Random r = new Random();
        food = gridList.get(r.nextInt(gridList.size()));
    }

    public void swipeUp() {
        if (dy != 1) {
            dy = -1;
            dx = 0;
        }
    }

    public void swipeDown() {
        if (dy != -1) {
            dy = 1;
            dx = 0;
        }
    }

    public void swipeLeft() {
        if (dx != 1) {
            dx = -1;
            dy = 0;
        }
    }

    public void swipeRight() {
        if (dx != -1) {
            dx = 1;
            dy = 0;
        }
    }

    public Bitmap toBitmap(int width, int height) {
        float cellWidth = width / cols;
        float cellHeight = height / rows;
        float avgRadius = (cellWidth + cellHeight) / 5;

        Bitmap ret = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            canvas.drawCircle(p.x * cellWidth, p.y * cellHeight, avgRadius, paint);
        }

        paint.setColor(Color.RED);
        canvas.drawCircle(food.x * cellWidth, food.y * cellHeight, avgRadius, paint);
        return ret;
    }
}
