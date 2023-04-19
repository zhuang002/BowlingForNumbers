import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {

	static int n,k,w;
	static int[] scores;
	static int[][] cacheHighScore;
	static int[] cacheSum1;
	static int[][] cacheSum2;

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(reader.readLine());
		for (int i=0;i<t;i++) {
			doTestCase(reader);
		}
	}

	private static void doTestCase(BufferedReader reader) throws IOException {
		// TODO Auto-generated method stub
		String[] nkw=reader.readLine().split(" ");
		n=Integer.parseInt(nkw[0]);
		k=Integer.parseInt(nkw[1]);
		w=Integer.parseInt(nkw[2]);
		
		scores = new int[n];
		for (int i=0;i<n;i++) {
			scores[i] = Integer.parseInt(reader.readLine());
		}
		
		cacheHighScore = new int[n][k+1];
		for (int i=0;i<n;i++) {
			for (int j=0;j<=k;j++) {
				cacheHighScore[i][j]=-1;
			}
		}
		
		cacheSum1 = new int[n];
		cacheSum1[n-1] = scores[n-1];
		for (int i=n-2;i>=0;i--) {
			cacheSum1[i] = cacheSum1[i+1]+scores[i];
		}
		
		cacheSum2 = new int[n][w+1];
		for (int i=0;i<cacheSum2.length;i++) {
			for (int j=1;j<=w;j++) {
				if (i+j-1<n)
					cacheSum2[i][j]=cacheSum2[i][j-1]+scores[i+j-1];
				else
					cacheSum2[i][j] = -1;
			}
		}
		
		System.out.println(getHighestScore(0,k));
	}

	private static int getHighestScore(int start, int kThrows) {
		// TODO Auto-generated method stub
		if (kThrows==0)
			return 0;
		int length=n-start;
		
		if (cacheHighScore[start][kThrows]!=-1)
			return cacheHighScore[start][kThrows];
		
		
		if (length<=3 || length<w*kThrows) {
			return cacheSum1[start];
		}

		
		int max = 0;


		
		int score = getHighestScore(start+1,kThrows);
		if (max<score) max=score;
		
		
		for (int hits=1;hits<=w;hits++) {
			score = getSum(start,hits)+getHighestScore(start+hits,kThrows-1);
			if (max<score) max=score;
		}
		
		
		cacheHighScore[start][kThrows] = max;
		return max;
	}

	private static int getSum(int start, int hits) {
		// TODO Auto-generated method stub
		if (hits == 0) return 0;
		if (hits == 1) return scores[start];
		if (hits == 2) return scores[start]+scores[start+1];
		
		if (cacheSum2[start][hits]!=-1) return cacheSum2[start][hits];
		int sum = getSum(start,hits-1) + scores[start+hits-1];
		cacheSum2[start][hits-1] = sum;
		return sum;
	}



}



