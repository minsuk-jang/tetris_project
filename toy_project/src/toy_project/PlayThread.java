package toy_project;

import java.util.*;

public class PlayThread extends Thread {
	private static boolean block_finish = false;
	private List<Point> b;
	private int[][] board = new int[20][10];
	private int[] ud = { 1, 0, 0 };
	private int[] rl = { 0, -1, 1 };
	public static int dir = 0;
	private long time = 1000;

	@Override
	public void run() {
		tetris_board tetris_board = new tetris_board();
		tetris_block block = new tetris_block();
		tetris_board.set_board(board);

		while (true) {
			try {
				if (!block_finish) {
					Random rand = new Random();
					int n = rand.nextInt(6);
					b = block.make_block(n);

					tetris_board.set_block(b);
					block_finish = true;
				}

				Thread.sleep(200);
				if (move()) {
					tetris_board.repaint();
				} else {
					block_finish = false;
					for (Point p : b) {
						board[p.y][p.x] = 1;
					}
					print();
				}

				dir = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void print() {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	private boolean move() {
		for (Point p : b) {
			int nx = p.y + ud[dir];
			int ny = p.x + rl[dir];

			if (nx < 0 || nx >= 20 || ny < 0 || ny >= 10)
				return false;

			if (board[nx][ny] == 1)
				return false;
		}

		for (Point p : b) {
			p.y += ud[dir];
			p.x += rl[dir];
		}
		return true;
	}
}