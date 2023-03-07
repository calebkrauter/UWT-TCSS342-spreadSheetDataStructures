package src;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class TopologicalSort {
	private TopologicalSort() {}
	
	public static Stack<Cell> sort(final Cell[][] theCellArr, final int[][] theIndegrees) {
		Stack<Cell> s = new Stack<Cell>();
		// stores the indexes of cells with 0 indegrees
		Queue<Integer> q = new LinkedList<Integer>();
		
		do {
			while (!q.isEmpty()) {
				int dq = q.poll();
				// obtains the cell from the 2d cell array
				Cell c = theCellArr[dq / theCellArr.length][dq % theCellArr.length];
				if (c.getDependents() == null)
					continue;
				
				// decrements the indegrees of the cell's dependents
				for (final CellToken dependents: c.getDependents()) {
					for (int i = 0; i < theCellArr.length * theCellArr.length; i++) {
						// simplifies code for better readability
						int row = i / theCellArr.length;
						int col = i % theCellArr.length;
						// decrements the indegrees
						if (theIndegrees[row][col] != -1 && 
							dependents.getRow() == row && dependents.getColumn() == col) {
							theIndegrees[row][col]--;
						}
					}
				}
			}
			
			// searches for indegrees of 0 and adds it to the queue and puts it in the output
			for (int i = 0; i < theCellArr.length * theCellArr.length; i++) {
				if (theIndegrees[i / theCellArr.length][i % theCellArr.length] == 0) {
					q.add(i);
					s.add(theCellArr[i / theCellArr.length][i % theCellArr.length]);
					theIndegrees[i / theCellArr.length][i % theCellArr.length]--;
				}
			}
		} while (!q.isEmpty());
		
		if (s.size() != theCellArr.length * theCellArr.length) {
			System.out.println("CYCLE FOUND!");
			return null;
		}
		return s;
	}
}
