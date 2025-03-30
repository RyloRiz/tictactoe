import java.util.Scanner;

public class TicTacToeTester
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		Board board = new Board();
		Bot bot = new Bot(board);

		int currentPlayer = 2;

		/*
		"Who's Turn Is It" Algorithm:

		2				PLAYER
		1-2 = -1		BOT
		1-(-1) = 2		PLAYER
		 */

		int count = 0;

		while (true)
		{
			count++;

			int location;

			if (currentPlayer == 2) {
				board.printBoard();
				System.out.print("Enter X's move # (top row = 1 2 3, middle row = 4 5 6, bottom row = 7 8 9): ");
				location = input.nextInt();
			}
			else
			{
				// do the bot move
				location = bot.getNextMove(board.getStringRepresentable(), 'O', 'X');
			}

			int row = (location - 1) / 3;
			int col = (location - 1) % 3;
			char playerChar = currentPlayer == 2 ? 'X' : 'O';
			boolean success = board.move(playerChar, row, col);

			if (success)
			{
				boolean isTie = board.checkTie();
				if (isTie)
				{
					board.printBoard();
					System.out.println("Tie game! No one wins.");
					break;
				}

				char winner = board.checkWinner();
				if (winner != ' ' || count > 50)
				{
					board.printBoard();
					System.out.println("Winner is " + winner + "!");
					break;
				}

				currentPlayer = 1 - currentPlayer;
			}
			else
			{
				System.out.println("Invalid move at " + location + " by " + playerChar + "!");
			}
		}
	}
}
