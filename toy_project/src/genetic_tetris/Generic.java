package genetic_tetris;

import java.util.*;

public class Generic {
	private Weight[] w;
	private int size;

	public Generic(int count) {
		this.size = count;
		w = new Weight[this.size];

		Random rn = new Random();
		for (int i = 0; i < w.length; i++) {
			int blank_weight = rn.nextInt(1000) + 1;
			int round_block_weight = rn.nextInt(1001) + 1;
			int complete_line_weight = rn.nextInt(3001) + 1000;
			int height_weight = rn.nextInt(100) + 1;
			int down_blank_weight = rn.nextInt(2000) + 1000;

			w[i] = new Weight(i + 1, 0, blank_weight, complete_line_weight, round_block_weight, height_weight,
					down_blank_weight);
		}
	}

	public Weight[] get_weight() {
		return w;

	}

	public void cross_over() {
		double result = Integer.MIN_VALUE;
		int idx = -1;

		// rank가 가장 큰 유전자 선택
		for (int i = 0; i < size; i++) {
			if (result < w[i].score) {
				result = w[i].score;
				idx = i;
			}
		}
		Weight fitness = w[idx];
		w = new Weight[this.size];

		int f_bw = fitness.blank_weight;
		int f_cl = fitness.complete_line_weight;
		int f_rb = fitness.round_block_weight;
		int f_hw = fitness.height_weight;
		int f_dbw = fitness.down_blank_weight;

		System.out.println("Select gene number : " + idx + " Score : " + fitness.score);
		System.out.println("blank : " + fitness.blank_weight);
		System.out.println("Complete line : " + fitness.complete_line_weight);
		System.out.println("round block : " + fitness.round_block_weight);
		System.out.println("height : " + fitness.height_weight);
		System.out.println("down blank : " + fitness.down_blank_weight);

		w[0] = fitness;
		w[0].number = 0;
		w[0].score = 0;

		int mutation = (Math.abs(f_bw) + Math.abs(f_cl) + Math.abs(f_rb) + Math.abs(f_hw) + Math.abs(f_dbw)) / 20;

		System.out.println("Mutation : " + mutation);
		Random rn = new Random();

		for (int i = 1; i < size; i++) {

			int b = (f_bw + rn.nextInt(mutation * 2));
			int cl = (f_cl + rn.nextInt(mutation * 2));
			int rb = (f_rb + rn.nextInt(mutation * 2));
			int hw = (f_hw + rn.nextInt(mutation * 2));
			int dbw = (f_dbw + rn.nextInt(mutation * 2));
			
			
			w[i] = new Weight(i + 1, 0, b, cl, rb, hw,dbw);
		}

		System.out.println("complete generate\n");
	}

	public class Weight {
		int number = 0;
		long score;
		int blank_weight;
		int complete_line_weight;
		int round_block_weight;
		int height_weight;
		int down_blank_weight;

		public Weight(int n, long s, int b, int cl, int rb, int hw, int dbw) {
			this.number = n;
			this.score = s;
			this.blank_weight = b;
			this.complete_line_weight = cl;
			this.round_block_weight = rb;
			this.height_weight = hw;
			this.down_blank_weight = dbw;
		}

	}
}
