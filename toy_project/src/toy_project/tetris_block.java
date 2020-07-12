package toy_project;

import java.util.*;

public class tetris_block {
	public List<Point> make_block(int j) {
		List<Point> list = new ArrayList<>();

		// ㅡ 모양 블록
		if (j == 0) {
			int[] x_idx = { 4, 5, 6, 7 };
			int[] y_idx = { 0, 0, 0, 0 };
			make(list,x_idx,y_idx);
			
		} else if (j == 1) {
			// ㅗ 모양 블록
			int[] x_idx = { 4, 5, 5, 6 };
			int[] y_idx = { 1, 0, 1, 1 };
			make(list,x_idx,y_idx);
			
		} else if (j == 2) {
			// ㄴ 모양 블록
			int[] x_idx = { 4, 5, 6, 6 };
			int[] y_idx = { 1, 1, 1, 0 };
			make(list,x_idx,y_idx);
			
		} else if (j == 3) {
			// ㄴ 모양 뒤집은 블록
			int [] x_idx = {4,4,5,6};
			int [] y_idx = {0,1,1,1};
			make(list,x_idx,y_idx);
			
		} else if (j == 4) {
			//나머지 모양
			int [] x_idx = {4,5,5,6};
			int [] y_idx = {0,0,1,1};
			make(list,x_idx,y_idx);
			
		}else {
			int [] x_idx = {4,5,5,6};
			int [] y_idx = {1,1,0,0};
			make(list,x_idx,y_idx);
			
		}

		return list;
	}
	
	private void make(List<Point> list, int[] x_idx ,int [] y_idx) {
		for(int i =0 ; i < 4 ;i++) {
			list.add(new Point(x_idx[i],y_idx[i]));
		}
	}

}
