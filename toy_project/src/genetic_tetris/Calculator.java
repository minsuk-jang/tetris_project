package genetic_tetris;

import java.util.*;

public class Calculator {
	private static int ud[] = { -1, 0, 1, 0 };
	private static int rl[] = { 0, 1, 0, -1 };

	public static Double blockFitness(double hw, double aw, double clw, double bw) {
		double result = 0.0;

		int hc = hole_count();
		int cl = complete_line();

		int[] height = new int[Generic.board[0].length];
		int ah = aggregate_height(height);
		int b = bumpiness(height);
		
		result += hc * hw;
		result += cl * clw;
		result += bw * b;
		result += ah * aw;

		//System.out.println("h : " + hc + " c : " + cl + " bw : " + b + " ah : " + ah);
		return result;
	}

	// 완성된 줄을 찾는 메소드
	private static int complete_line() {
		int ret = 0;
		for (int i = 0; i < Generic.board.length; i++) {
			int count = 0;
			for (int j = 0; j < Generic.board[0].length; j++) {
				if (Generic.board[i][j] != 0)
					count++;
			}

			if (count == Generic.board[0].length)
				ret++;
		}

		return ret;
	}

	private static int bumpiness(int height[]) {
		int ret = 0;
		for (int i = 1; i < height.length; i++) {
			ret += Math.abs(height[i - 1] - height[i]);
		}

		return ret;
	}

	private static int aggregate_height(int height[]) {
		for (int i = 0; i < Generic.board[0].length; i++) {
			int startX = 0;
			while (true) {
				if (startX >= 20  || Generic.board[startX][i] != 0) {
					break;
				}
				startX++;
			}

			height[i] = startX;
		}

		int ret = 0;

		for (int i = 0; i < height.length; i++) {
			ret += height[i];
		}

		return ret;
	}

	// 테트리스 블럭들 사이에 존재하는 구멍을 구하는 메소드
	private static int hole_count() {
		boolean[][] visited = new boolean[Generic.board.length][Generic.board[0].length];

		// 테트리스 보드판에 0이 아닌 지점을 찾는다.
		for (int i = 0; i < Generic.board.length; i++) {
			for (int j = 0; j < Generic.board[i].length; j++) {
				if (Generic.board[i][j] != 0)
					visited[i][j] = true;
			}
		}

		// (0,4)를 기준으로 해서 bfs를 진행한다.
		bfs(0, 4, visited);

		int ret = 0;

		// bfs를 진행하고 boolean 값이 false인 값은 구멍이다.
		for (int i = 0; i < visited.length; i++) {
			for (int j = 0; j < visited[0].length; j++) {
				if (!visited[i][j])
					ret++;
			}
		}

		return ret;
	}

	private static void bfs(int startX, int startY, boolean[][] visited) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(startX, startY));
		visited[startX][startY] = true;

		while (!q.isEmpty()) {
			Point cur = q.poll();

			for (int i = 0; i < 4; i++) {
				int nx = cur.x + ud[i];
				int ny = cur.y + rl[i];

				if (nx < 0 || nx >= visited.length || ny < 0 || ny >= visited[0].length || visited[nx][ny])
					continue;

				visited[nx][ny] = true;
				q.add(new Point(nx, ny));
			}
		}
	}

}
