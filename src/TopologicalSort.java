package src;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class TopologicalSort {
	private TopologicalSort() {}
	
	public static Stack<Cell> sort(final Cell[][] theCellArr, final int[][] theIndegrees) {
		Stack<Cell> s = new Stack<Cell>();
		Queue<Integer> q = new LinkedList<Integer>();
		
		do {
			while (!q.isEmpty()) {
				int dq = q.poll();
				Cell c = theCellArr[dq / theCellArr.length][dq % theCellArr.length];
				if (c.getDependents() == null)
					continue;
				
				// decrements the indegrees of the cell's dependents
				for (final CellToken dependents: c.getDependents()) {
					for (int i = 0; i < theCellArr.length * theCellArr.length; i++) {
						if (theIndegrees[i / theCellArr.length][i % theCellArr.length] != -1 &&
							dependents.getRow() == i / theCellArr.length && 
							dependents.getColumn() == i % theCellArr.length) {
							theIndegrees[i / theCellArr.length][i % theCellArr.length]--;
						}
					}
				}
			}
			
			// searches for indegrees of 0 and adds it to the queue and puts it in the ouput
			for (int i = 0; i < theCellArr.length * theCellArr.length; i++) {
				if (theIndegrees[i / theCellArr.length][i % theCellArr.length] == 0) {
					q.add(i);
					s.add(theCellArr[i / theCellArr.length][i % theCellArr.length]);
					theIndegrees[i / theCellArr.length][i % theCellArr.length]--;
				}
			}
		} while (!q.isEmpty());
		
		return s;
	}
}
