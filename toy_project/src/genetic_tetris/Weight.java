package genetic_tetris;

public class Weight {
	int number = 0;
	int line = 0;
	long score;
	double SIX = 1000000.0;

	double hole_weight, bumpiness_weight, complete_line_weight, aggregate_height_weight;

	public Weight(int n, int l, long s, double hw, double bw, double clw, double ahw) {
		this.number = n;
		this.line = l;
		this.score = s;

		// 4가지 가중치 설정, 6자리 수까지 구한 후, 나머지는 반올림
		this.aggregate_height_weight = Math.round(ahw * SIX) / SIX;
		this.hole_weight = Math.round(hw * SIX) / SIX;
		this.complete_line_weight = Math.round(clw * SIX) / SIX;
		this.bumpiness_weight = Math.round(bw * SIX) / SIX;
	}

}