package toy_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class tetris_board extends JFrame {
	private MyPanel panel = new MyPanel();
	private int[][] board;
	private List<Point> block;
	
	public tetris_board() {
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 700);
		setResizable(false);
		setContentPane(panel); //setContentPane을 이용하여 panel을 부착하게 되면 테트리스를 표현할 수 있다.
		setLocation(500,200);
		setVisible(true);
	}

	//PlayThread에서 만들어진 테트리스 블럭을 설정한다.
	public void set_block(List<Point> b) {
		this.block = b;
	}

	//PlayThread에서 만들어진 테트리스 보드판을 표현하는 이차원 int형 배열을 설정한다.
	public void set_board(int[][] board) {
		this.board = board;
	}

	/*
	 * 테트리스를 표현하는 Panel 클래스
	 * 생성자에서 키 이벤트를 부착한다.
	 */
	private class MyPanel extends JPanel {
		public MyPanel() {
			this.setFocusable(true);
			this.addKeyListener(new KeyListener());
			this.requestFocus();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			draw_board(g);
		}

		/*
		 * 테트리스 보드판과 테트리스 블럭을 그리는 메소드
		 * swing의 좌표 개념과 통상적으로 우리가 코딩할 때 생각하는 좌표의 개념이 다르기 때문에 주의해야 한다.
		 */
		public void draw_board(Graphics g) {
			g.setColor(Color.cyan);
			
			for(Point p : block) {
				g.fillRect(p.x*30, p.y*30, 30, 30);
			}
			
			for (int i = 0; i < 20; i++) {
				for (int j = 0; j < 10; j++) {
					if (board[i][j] == 1) {
						g.fillRect(j * 30, i * 30, 30, 30);
					}
				}
			}

			g.setColor(Color.black);
			for (int i = 1; i <= 10; i++) {
				g.drawLine(i * 30, 0, i * 30, 600);
			}

			for (int i = 1; i <= 20; i++) {
				g.drawLine(0, i * 30, 300, i * 30);
			}

			g.setFont(new Font("Arial",Font.ITALIC,30));
			g.drawString("lines: " + PlayThread.removed_line, 330, 330);
		}
	}

}
