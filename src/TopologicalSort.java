package src;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class TopologicalSort {
	private TopologicalSort() {}
	
	public static Stack<Cell> sort(final Cell[][] theCellArr, final int[][] theIndegrees) {
		Stack<Cell> s = new Stack<>();
		Queue<Integer> q = new LinkedList<>();
		int len = theCellArr.length;
		
		// Add cells with 0 indegrees to the queue
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < theCellArr[i].length; j++) {
				if (theIndegrees[i][j] == 0) {
					q.add(i * len + j);
				}
			}
		}
		
		while (!q.isEmpty()) {
			int dq = q.poll();
			Cell c = theCellArr[dq / len][dq % len];
			
			// If the cell has no dependents, skip to the next iteration
			if (c.getDependents() == null) {
				continue;
			}
			
			// Decrement the indegrees of the cell's dependents
			for (final CellToken dependents : c.getDependents()) {
				int row = dependents.getRow();
				int col = dependents.getColumn();
				
				if (theIndegrees[row][col] != -1) {
					theIndegrees[row][col]--;
					if (theIndegrees[row][col] == 0) {
						q.add(row * len + col);
					}
				}
			}
			
			// Add the processed cell to the output stack
			s.add(c);
		}
		
		if (s.size() != len * theCellArr[0].length) {
			return null;
		}
		
		return s;
	}
}
