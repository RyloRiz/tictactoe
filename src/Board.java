import java.util.Arrays;

public class Board
{
	private final char[][] board;

	public Board()
	{
		board = new char[3][3];

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				board[i][j] = ' ';
			}
		}

//		System.out.println(Arrays.deepToString(board));
	}

	public void printBoard()
	{
		System.out.println("┏━━━┳━━━┳━━━┓"); // Top border
		System.out.println("┃ " + board[0][0] + " ┃ " + board[0][1] + " ┃ " + board[0][2] + " ┃"); // First row
		System.out.println("┣━━━╋━━━╋━━━┫"); // Top-Middle border
		System.out.println("┃ " + board[1][0] + " ┃ " + board[1][1] + " ┃ " + board[1][2] + " ┃"); // Second row
		System.out.println("┣━━━╋━━━╋━━━┫"); // Middle-Bottom border
		System.out.println("┃ " + board[2][0] + " ┃ " + board[2][1] + " ┃ " + board[2][2] + " ┃"); // Third row
		System.out.println("┗━━━┻━━━┻━━━┛"); // Bottom border

		System.out.println();
	}

	public boolean move(char player, int row, int col)
	{
		char currentValue = board[row][col];

		if (currentValue == ' ')
		{
			board[row][col] = player;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean checkTie()
	{
		for (char[] row : board)
		{
			for (char c : row)
			{
				if (c == ' ')
					return false;
			}
		}

		return true;
	}

	public char checkWinner()
	{
		char rows = checkRows();
		if (rows != ' ')
			return rows;

		char cols = checkColumns();
		if (cols != ' ')
			return cols;

		return checkDiagonals();
	}

	public char checkRows()
	{
		for (int i = 0; i < 3; i++)
		{
			char left = board[i][0];
			char mid = board[i][1];
			char right = board[i][2];

			if (left == mid && mid == right && mid != ' ')
				return mid;
		}

		return ' ';
	}

	public char checkColumns()
	{
		for (int i = 0; i < 3; i++)
		{
			char top = board[0][i];
			char mid = board[1][i];
			char bottom = board[2][i];

			if (top == mid && mid == bottom && mid != ' ')
				return mid;
		}

		return ' ';
	}

	public char checkDiagonals()
	{
		char tl = board[0][0];
		char tr = board[0][2];
		char mid = board[1][1];
		char bl = board[2][0];
		char br = board[2][2];

		boolean negativeSlope = (tl == mid && mid == br && mid != ' ');
		boolean positiveSlope = (bl == mid && mid == tr && mid != ' ');

		if (negativeSlope || positiveSlope)
			return mid;

		return ' ';
	}

	public String getStringRepresentable()
	{
		StringBuilder repr = new StringBuilder();

		for (char[] row : board)
		{
			for (char c : row)
			{
				repr.append(c);
			}
		}

		return repr.toString();
	}
}
