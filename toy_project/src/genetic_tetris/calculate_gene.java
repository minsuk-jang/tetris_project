package genetic_tetris;

import java.util.*;

public class calculate_gene {
	private int[][] board;
	private int[] ud = { -1, 0, 1, 0 };
	private int[] rl = { 0, 1, 0, -1 };
	private int [] height;

	public double calculate(List<Point> tmp, int n, int[][] board, Weight w) {
		this.board = board;

		// 현재 블럭으로 채우기
		for (Point p : tmp) {
			board[p.x][p.y] = n;
		}

		int cl = complete_line();
		int h = hole();
		int aggregate = aggregate_height();
		int bump = bumpiness();
		
		double result = ((cl * w.complete_line_weight)+ (h * w.hole_weight) + (bump * w.bumpiness_weight) + 
				(w.aggregate_height_weight * aggregate)) / 50;
		
		
		return result;
	}
	
	private void print(int cl ,int h , int agg ,int bump) {
		System.out.println("Complete line: " + cl + " hole: " + h + " aggregate height: " + agg + " bumpiness: " + bump);
	}
	
	private int complete_line() {
		int ret =0 ;
		
		int line = 0;
		for(int i= 0 ; i < board.length ; i++) {
			int count =0 ;
			for(int j =0 ; j < board[i].length ; j++) {
				if(board[i][j] != 0)
					count++;
			}
			
			if(count == 10)
				ret++;
		}
		
		return ret;
	}
	
	private int hole() {
		boolean [][] visited= new boolean[20][10];
		
		//테트리스 블럭을 채움
		for(int i =0 ; i < visited.length ; i ++) {
			for(int j =0 ; j < visited[i].length; j ++) {
				if(board[i][j] != 0)
					visited[i][j] = true;
			}
		}
		
		bfs(0,4,visited);
		int ret = 0;
		for(int i =0 ; i < visited.length; i ++) {
			for(int j =0 ; j < visited[i].length ; j++) {
				if(!visited[i][j])
					ret++;
			}
		}
		
		return ret;
	}
	
	private void bfs(int startX ,int startY, boolean [][] visited) {
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(startX,startY));
		visited[startX][startY]  = true;
		
		while(!q.isEmpty()) {
			Point cur = q.poll();
			
			for(int i=0 ; i  <4 ; i++) {
				int nx = cur.x  + ud[i];
				int ny = cur.y + rl[i];
				
				if(nx <0 || nx >= 20 || ny < 0 || ny>= 10 || visited[nx][ny])
					continue;
				
				visited[nx][ny] = true;
				q.add(new Point(nx,ny));
			}
		}
	}
	
	private int bumpiness() {
		int ret =0 ;
		for(int i = 1 ; i < board[0].length ; i++) {
			ret += Math.abs(height[i-1] - height[i]);
		}
		
		return ret;
	}
	
	private int aggregate_height() {
		int ret =0 ;
		height = new int[10];
		for(int i =0 ; i < board[0].length ; i++) {
			int start = 0;
			
			while(start< 20) {
				if(board[start][i] == 0)
					start++;
				else
					break;
			}
			height[i] = start;
			ret += start;
		}
		
		return ret;
	}
}
