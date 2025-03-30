import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bot
{
	private final static String[] winningCodes = new String[] {"123", "456", "789", "147", "258", "369", "159", "357"};

	private final Board board;

	public Bot(Board board)
	{
		this.board = board;
	}

	public int getNextMove(String repr, char player, char opponent)
	{
		if (repr.indexOf(' ') == -1)
			return 0;

		int[] moves = new int[] {5, 1, 3, 7, 9, 2, 4, 6, 8};
		HashMap<Integer, Long> scores = new HashMap<Integer, Long>();

		for (int move : moves)
		{
			// check can do move
			if (repr.charAt(move-1) != ' ')
				continue;

			// get codes
			String newPlayerRepr = repr.substring(0, move-1) + player + repr.substring(move-1);
			String newOppRepr = repr.substring(0, move-1) + opponent + repr.substring(move-1);

			String prePlayerCode = getCodeFromRepr(repr, player);
			String postPlayerCode = getCodeFromRepr(newPlayerRepr, player);
			String preOppCode = getCodeFromRepr(repr, opponent);
			String postOppCode = getCodeFromRepr(newOppRepr, opponent);

			// rate each code
			long prePlayerScore = rateCode(prePlayerCode);
			long postPlayerScore = rateCode(postPlayerCode);
			long preOppScore = rateCode(preOppCode);
			long postOppScore = rateCode(postOppCode);

			// get diffs
			long deltaPlayer = postPlayerScore - prePlayerScore; // (-) = bad move, (+) = good move
			long deltaOpp = postOppScore - preOppScore; // (-) = good move, (+) = bad move

			long moveBenefit = deltaPlayer - deltaOpp;
			long futureMoveBenefit = getNextMove(newPlayerRepr, player, opponent);

			scores.put(move, moveBenefit + futureMoveBenefit);
		}

//		long max = scores.values().stream().max(Long::compareTo).get();

		int maxKey = 0;
		long max = 0;
		for (Integer key : scores.keySet()) {
			Long value = scores.get(key);
			if (value > max)
			{
				maxKey = key;
				max = value;
			}
		}

		return maxKey;
	}

	public long rateCode(String code)
	{
		if (Arrays.asList(winningCodes).contains(code))
			return 10; // Perfect

		double FACTOR = 8;

		// split code up in segments
		String[] segments = getAllSegments(code);

		// get # of occurances of each segment
		HashMap<String, Double> segmentScores = new HashMap<String, Double>();
		for (String segment : segments)
		{
			double ocs = 0;
			for (String winningCode : winningCodes)
			{
				if (winningCode.contains(segment))
					ocs++;
			}
			segmentScores.put(segment, ocs * segment.length() / FACTOR);
		}

		// calculate SUM of: (# of occurances) * (length of segment) / (FACTOR=8)
		double sumOfSegmentScores = segmentScores.values().stream().mapToDouble(Double::valueOf).sum();
		return Math.round(sumOfSegmentScores);
	}

	public String[] getAllSegments (String str) {
		int len = str.length();
		String[] segments = new String[len * (len + 1) / 2];
		int k = 0;

		for (int i = 0; i < str.length(); i++) {
			for (int j = i + 1; j <= str.length(); j++) {
				segments[k] = str.substring(i, j);
				k++;
			}
		}

		return segments;
	}

	public String getCodeFromRepr(String repr, char iden)
	{
		StringBuilder code = new StringBuilder();

		for (int i = 0; i < repr.length(); i++)
		{
			char ch = repr.charAt(i);
			if (ch == iden)
				code.append(i+1);
		}

		return code.toString();
	}

	/*

	"123456789"

	"OOO      "		-> 	123		->	+1, >0, (n-1) / 3 == 0
	"   OOO   "		->	456		->	+1, >3, (n-1) / 3 == 1
	"      OOO"		->	789		->	+1, >6, (n-1) / 3 == 2

	"O  O  O  "		->	147		->	+3, >0, (n-1) % 3 == 0
	" O  O  O "		->	258		->	+3, >2, (n-1) % 3 == 1
	"  O  O  O"		->	369		->	+3, >3, (n-1) % 3 == 2

	"O   O   O"		->	159		->	+4, 1-9, n % 4 == 1
	"  O O O  "		->	357		->	+2, 3-7, RULE NEEDED

	-	%3	%4		SUM		DIFF	MUL		DIV		MOD		SUM+DIFF=MUL-MOD
	1	1	1		2		0		1		1		0
	2	2	2		4		0		4		1		0
	3	0	3	**	3		-3		0		0		0
	4	1	0		1		1		0		NA		NA
	5	2	1	**	3		1		2		2		0
	6	0	2		2		-2		0		0		0
	7	1	3	**	4		-2		3		0		1
	8	2	0		2		2		0		NA		NA
	9	0	1		1		-1		0		0		0

	((n%3)+(n%4))+((n%3)-(n%4))==((n%3)*(n%4))((n%3)/(n%4))

	main():
	- initial()
	- preventXWin()
	- attainOWin()

	initial():
	- get middle (5) spot: "    O    "
	- get corner (1, 3, 7, 9) spot
	- get side (2, 4, 6, 8) spot

	preventXWin():
	-

	attainOWin():
	-

	matchWinningLayouts(PLR):
	-

	 */


//	public int nextMove()
//	{
//		String repr = board.getStringRepresentable();
//		return solve(repr, 'O');
//	}
//
//	public int solve(String repr, char iden)
//	{
//		String code = getCodeFromRepr(repr, iden);
//
//	}
}
