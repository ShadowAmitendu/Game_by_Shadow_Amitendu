package com.shadow.gamebyshadowamitendu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColour;
    private final int XColour;
    private final int OColour;
    private final int winningLineColour;
    private boolean winningLine = false;

    private final Paint paint = new Paint();

    private int cellSize;
    private final GameLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TicTacToeBoard, 0, 0);

        try {
            boardColour = a.getInteger(R.styleable.TicTacToeBoard_boardColour, 0);
            XColour = a.getInteger(R.styleable.TicTacToeBoard_XColour, 0);
            OColour = a.getInteger(R.styleable.TicTacToeBoard_OColour, 0);
            winningLineColour = a.getInteger(R.styleable.TicTacToeBoard_winningLineColour, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (winningLine) {
            paint.setColor(winningLineColour);
            drawWinningLine(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int row = (int) (y / cellSize);
            int col = (int) (x / cellSize);

            if (!winningLine && game.updateGameBoard(row + 1, col + 1)) {
                invalidate(); // Redraw the board

                if (game.winnerCheck()) {
                    winningLine = true;
                    invalidate(); // Redraw to show the winning line
                }

                // Update player turn
                game.setPlayer(game.getPlayer() % 2 == 0 ? game.getPlayer() - 1 : game.getPlayer() + 1);
            }

            return true;
        }

        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColour);
        paint.setStrokeWidth(16);

        // Draw vertical lines
        for (int c = 1; c < 3; c++) {
            canvas.drawLine(cellSize * c, 0, cellSize * c, canvas.getHeight(), paint);
        }

        // Draw horizontal lines
        for (int r = 1; r < 3; r++) {
            canvas.drawLine(0, cellSize * r, canvas.getWidth(), cellSize * r, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getGameBoard()[r][c] != 0) {
                    if (game.getGameBoard()[r][c] == 1) {
                        drawX(canvas, r, c);
                    } else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col) {
        paint.setColor(XColour);

        float startX = col * cellSize + cellSize * 0.2f;
        float startY = row * cellSize + cellSize * 0.2f;
        float endX = (col + 1) * cellSize - cellSize * 0.2f;
        float endY = (row + 1) * cellSize - cellSize * 0.2f;

        canvas.drawLine(startX, startY, endX, endY, paint);
        canvas.drawLine(startX, endY, endX, startY, paint);
    }

    private void drawO(Canvas canvas, int row, int col) {
        paint.setColor(OColour);

        float left = col * cellSize + cellSize * 0.2f;
        float top = row * cellSize + cellSize * 0.2f;
        float right = (col + 1) * cellSize - cellSize * 0.2f;
        float bottom = (row + 1) * cellSize - cellSize * 0.2f;

        canvas.drawOval(left, top, right, bottom, paint);
    }

    private void drawWinningLine(Canvas canvas) {
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        int lineType = game.getWinType()[2];

        paint.setStrokeWidth(10);
        paint.setColor(winningLineColour);

        switch (lineType) {
            case 1: // Horizontal
                canvas.drawLine(0, row * cellSize + cellSize / 2, canvas.getWidth(), row * cellSize + cellSize / 2, paint);
                break;
            case 2: // Vertical
                canvas.drawLine(col * cellSize + cellSize / 2, 0, col * cellSize + cellSize / 2, canvas.getHeight(), paint);
                break;
            case 3: // Diagonal top-left to bottom-right
                canvas.drawLine(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
                break;
            case 4: // Diagonal bottom-left to top-right
                canvas.drawLine(0, canvas.getHeight(), canvas.getWidth(), 0, paint);
                break;
        }
    }

    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] name) {
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(name);
    }

    public void resetGame() {
        game.resetGame();
        winningLine = false;
        invalidate(); // Redraw the board
    }
}
