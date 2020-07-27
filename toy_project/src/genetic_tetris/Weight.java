package genetic_tetris;

public class Weight {
	int number = 0;
	int line = 0;
	long score;
	double[] variation;
	double [] mod = {8}; 
	
	public Weight(int n, int l, long s, double[] w) {
		this.number = n;
		this.line = l;
		this.score = s;
		this.variation = new double[9];

		for (int i = 0; i < 9; i++) {
			this.variation[i] = Math.round(w[i] *1000.0)/1000.0;
		}

		
	}

}