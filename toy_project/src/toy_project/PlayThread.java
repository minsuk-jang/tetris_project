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
	public static int removed_line = 0;
	private tetris_board tetris_board;

	@Override
	public void run() {
		tetris_board = new tetris_board();
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

				Thread.sleep(150);
				if (dir < 3)
					move();
				else if (dir == 3) // 위 키를 누른 경우
					rotate();
				else if (dir == 32) { // 스페이스 바를 누른 경우
					dir = 0 ;
					while (true) {
						if (!move()) {
							break;
						}
					}
				}
				tetris_board.repaint();
				dir = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 블록 회전 하는 메소드 회전 변환 행렬을 이용하여 회전
	 */
	private void rotate() {
		int pivot_x = b.get(1).x, pivot_y = b.get(1).y;
		boolean check = true;

		List<Point> tmp = new ArrayList<>();
		for (Point p : b) {
			tmp.add(new Point(p.x, p.y));
		}

		for (Point p : tmp) {
			p.x = p.x - pivot_x;
			p.y = p.y - pivot_y;
		}

		for (Point p : tmp) {
			int nx = p.x * 0 + -1 * p.y;
			int ny = p.x * 1 + 0 * p.y;

			p.x = nx + pivot_x;
			p.y = ny + pivot_y;

			if (p.x < 0 || p.x >= 10 || p.y < 0 || p.y >= 20) {
				check = false;
				break;
			}
		}

		if (check) {
			for (int i = 0; i < tmp.size(); i++) {
				b.get(i).x = tmp.get(i).x;
				b.get(i).y = tmp.get(i).y;
			}
		}

	}

	private void remove_line() {
		Set<Integer> x = new LinkedHashSet<>();

		for (Point p : b) {
			x.add(p.y);
		}

		for (int idx : x) {
			int count = 0;
			for (int j = 0; j < 10; j++) {
				if (board[idx][j] == 1)
					count++;
			}

			if (count == 10) {
				removed_line++;
				for (int j = 0; j < 10; j++)
					board[idx][j] = 0;

				for (int i = idx - 1; i >= 0; i--) {
					for (int j = 0; j < 10; j++) {
						if (board[i][j] == 1) {
							board[i][j] = 0;
							board[i + 1][j] = 1;
						}
					}
				}
			}
		}

	}

	private boolean move() {
		boolean check = true;
		for (Point p : b) {
			int nx = p.y + ud[dir];
			int ny = p.x + rl[dir];

			// 테트리스 블록이 배열 범위 밖으로 넘어갈 경우
			if (nx < 0 || nx >= 20 || ny < 0 || ny >= 10) {
				if (nx < 0 || nx >= 20) {
					block_finish = false;
					board_print();
					remove_line();
				}
				check = false;
				return check;
			}

			// 테트리스 블록이 존재할 경우
			if (board[nx][ny] == 1) {
				// 방향이 아랫방향일 경우
				if (dir == 0) {
					block_finish = false;
					board_print();
					remove_line();
				}
				check = false;
				return check;
			}
		}

		if (check) {
			for (Point p : b) {
				p.y += ud[dir];
				p.x += rl[dir];
			}
		}

		return check;
	}

	// 보드판에 블록 추가
	private void board_print() {
		for (Point p : b) {
			board[p.y][p.x] = 1;
		}
	}
}
