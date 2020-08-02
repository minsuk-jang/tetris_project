package toy_project;

import java.util.*;

//테트리스 블럭을 만드는 클래스
public class tetris_block {
	/*
	 * 매개변수에 상응하는 테트리스 블럭을 만드는 메소드
	 * 배열이 아닌 리스트를 이용해서 테트리스 블럭을 만든다.
	 * 기준은 x좌표가 0인 곳을 기준으로 하여 각각의 테트리스 블럭을 만든다.
	 */
	public List<Point> make_block(int j) {
		List<Point> list = new ArrayList<>();

		if (j == 0) {
			int[] x_idx = { 4, 5, 6, 7 };
			int[] y_idx = { 0, 0, 0, 0 };
			make(list, x_idx, y_idx);

		} else if (j == 1) {
			int[] x_idx = { 4, 5, 5, 6 };
			int[] y_idx = { 1, 1, 0, 1 };
			make(list, x_idx, y_idx);

		} else if (j == 2) {

			int[] x_idx = { 4, 6, 5, 6 };
			int[] y_idx = { 1, 1, 1, 0 };
			make(list, x_idx, y_idx);

		} else if (j == 3) {
			int[] x_idx = { 4, 4, 5, 6 };
			int[] y_idx = { 0, 1, 1, 1 };
			make(list, x_idx, y_idx);

		} else if (j == 4) {
			int[] x_idx = { 4, 5, 5, 6 };
			int[] y_idx = { 0, 0, 1, 1 };
			make(list, x_idx, y_idx);

		} else if (j == 5) {
			int[] x_idx = { 4, 5, 5, 6 };
			int[] y_idx = { 1, 1, 0, 0 };
			make(list, x_idx, y_idx);
		} else {
			int[] x_idx = { 4, 5, 4, 5 };
			int[] y_idx = { 0, 0, 1, 1 };

			make(list, x_idx, y_idx);
		}

		return list;
	}

	private void make(List<Point> list, int[] x_idx, int[] y_idx) {
		for (int i = 0; i < 4; i++) {
			list.add(new Point(x_idx[i], y_idx[i]));
		}
	}
}
