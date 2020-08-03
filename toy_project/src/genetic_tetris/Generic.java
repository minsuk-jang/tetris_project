package genetic_tetris;

import java.util.*;
import java.io.*;

/*
 * 664줄 : 7.729 4.579 3.201 2.851 8.467 3.576 10.293 1.159 -0.305 
 */

public class Generic {
	private Weight[] w;
	private int size;

	public Generic(int count) {
		this.size = count;
		w = new Weight[this.size];

		for (int i = 0; i < w.length; i++) {
			double hw = Math.random() * (2 *1) - 1;
			double bw = Math.random() * (2 * 1) - 1;
			double aw = Math.random() * (2 * 1) - 1;
			double cw = Math.random() * (2 * 1) - 1;

			w[i] = new Weight(i + 1, 0, 0, hw, bw, cw, aw);
		}
	}

	public Weight[] get_weight() {
		return w;

	}

	public void cross_over() {
		List<Weight> temp = new ArrayList<>();

		int avg = 0;


		int big = 0, big_idx = 0;
		for (int i = 0; i < w.length; i++) {
			avg += w[i].line;			

			if (big < w[i].line) {
				big = w[i].line;
				big_idx = i;
			}
		}

		avg /= w.length;

		for (int i = 0; i < w.length; i++) {
			if (avg <= w[i].line)
				temp.add(w[i]);
		}

		List<Weight> children = new ArrayList<>();
		
		System.out.print("Size: " + temp.size() + " ");
		print(w[big_idx]);
		
		
		make_children(children, temp); // 자식들을 만든다.
		adjust_mutation(children,temp);

		for (int i = 0; i < children.size(); i++) {
			children.get(i).line = 0;
			children.get(i).number = i + 1;
			children.get(i).score = 0;
			w[i] = children.get(i);
		}

		System.out.println("Complete Generation\n");
	}

	private void print(Weight c) {
		System.out.println("#" + c.number + " line: " + c.line + " score: " + c.score);
		System.out.println("hole: " + c.hole_weight + " | bumpiness: " + c.bumpiness_weight + " | Complete line: "
				+ c.complete_line_weight + " | Aggregate Height: " + c.aggregate_height_weight);

	}

	private void adjust_mutation(List<Weight> children,List<Weight> parent) {
		boolean[] visited = new boolean[parent.size()];
		int size = (int) (children.size() * 0.1);
		Random rn = new Random();

		for (int k = 0; k < size; k++) {
			int i = getIdx(visited);
			int idx1 = rn.nextInt(4);
			int idx2 = rn.nextInt(4);

			int small = Math.min(idx1, idx2);
			int big = Math.max(idx1, idx2);

			double[] temp = { children.get(i).hole_weight, children.get(i).bumpiness_weight,
					children.get(i).complete_line_weight, children.get(i).aggregate_height_weight };

			for (int j = small; j <= big; j++) {
				double mutation = Math.random() * (2 * 0.5) - 0.5;
				temp[j] += mutation;
			}
		}
	}

	private int getIdx(boolean[] visited) {
		Random rn = new Random();
		while (true) {
			int idx = rn.nextInt(visited.length);
			if (!visited[idx]) {
				visited[idx] = true;
				return idx;
			}
		}
	}

	private void make_children(List<Weight> children, List<Weight> parent) {
		if (parent.size() % 2 != 0) {
			int idx = 0;
			int small = Integer.MAX_VALUE;
			for (int i = 0; i < parent.size(); i++) {
				if (small > parent.get(i).line) {
					small = parent.get(i).line;
					idx = i;
				}
			}

			parent.remove(idx);
		}

		make(children, parent);
	}

	private void make(List<Weight> children, List<Weight> parent) {
		Random rn = new Random();
		while (children.size() != this.size) {
			int idx1 = rn.nextInt(parent.size());
			int idx2 = 0;
			while (true) {
				idx2 = rn.nextInt(parent.size());
				if (idx2 != idx1)
					break;
			}

			addChildren(parent.get(idx1), parent.get(idx2), children);
		}
	}

	private void addChildren(Weight p1, Weight p2, List<Weight> children) {
		double p1_variation[] = { p1.hole_weight, p1.bumpiness_weight, p1.complete_line_weight,
				p1.aggregate_height_weight };
		double p2_variation[] = { p2.hole_weight, p2.bumpiness_weight, p2.complete_line_weight,
				p2.aggregate_height_weight };

		Random rn = new Random();
		int idx1 = rn.nextInt(4);
		int idx2 = rn.nextInt(4);

		int small = Math.min(idx1, idx2);
		int big = Math.max(idx1, idx2);

		double temp[] = new double[4];
		double temp2[] = new double[4];
		System.arraycopy(p1_variation, 0, temp, 0, 4);
		System.arraycopy(p2_variation, 0, temp2, 0, 4);

		for (int i = small; i <= big; i++) {
			swap(temp, temp2, i);
		}

		children.add(new Weight(0, 0, 0, temp[0], temp[1], temp[2], temp[3]));
		children.add(new Weight(0, 0, 0, temp2[0], temp2[1], temp2[2], temp2[3]));

	}

	private void swap(double[] src, double[] dest, int idx) {
		double temp = src[idx];
		src[idx] = dest[idx];
		dest[idx] = temp;

	}
}
