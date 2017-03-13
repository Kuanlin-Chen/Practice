class SpiralMatrix
{
	static int[][] spiral;
	static int w, h;
	public static void main(String[] args)
	{
		java.util.Scanner sc = new java.util.Scanner(System.in);
		System.out.print("Please enter width:");
		w = sc.nextInt();
		System.out.print("Please enter height:");
		h = sc.nextInt();
		matrix(w, h);
		display();
	}

	static void matrix(int w, int h)
	{
		spiral = new int[w][h];
		int value = 1, x = 0, y = 0;
		while(true)
		{
			spiral[x][y] = value++;
			boolean right = x < w-1 && spiral[x+1][y] == 0;
			boolean down = y < h-1 && spiral[x][y+1] == 0;
			boolean left = x > 0 && spiral[x-1][y] == 0;
			boolean up = y > 0 && spiral[x][y-1] == 0;

			if (right) if (up) y--; else x++;
			else if (down) y++;
			else if (left) x--;
			else if (up) y--;
			else break;
		}

	}

	static void display()
	{
		for(int j = 0; j<h; j++){
			for(int i = 0; i<w; i++){
				int value = spiral[i][j];
				if (value<10) {
					System.out.print("0"+String.valueOf(value)+" ");
				} else {
					System.out.print(String.valueOf(value)+" ");
				}
			}
			System.out.println();
		}
	}
}