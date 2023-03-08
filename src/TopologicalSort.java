package src;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public final class TopologicalSort {
	private TopologicalSort() {}
	
	//	public static Stack<Cell> sort(final Cell[][] theCellArr, final int[][] theIndegrees) {
//		Stack<Cell> s = new Stack<Cell>();
//		// stores the indexes of cells with 0 indegrees
//		Queue<Integer> q = new LinkedList<Integer>();
//
//		do {
//			while (!q.isEmpty()) {
//				int dq = q.poll();
//				// obtains the cell from the 2d cell array
//				Cell c = theCellArr[dq / theCellArr.length][dq % theCellArr.length];
//				if (c.getDependents() == null)
//					continue;
//
//				// decrements the indegrees of the cell's dependents
//				for (final CellToken dependents: c.getDependents()) {
//					for (int i = 0; i < theCellArr.length * theCellArr.length; i++) {
//						// simplifies code for better readability
//						int row = i / theCellArr.length;
//						int col = i % theCellArr.length;
//						// decrements the indegrees
//						if (theIndegrees[row][col] != -1 &&
//							dependents.getRow() == row && dependents.getColumn() == col) {
//							theIndegrees[row][col]--;
//						}
//					}
//				}
//			}
//
//			// searches for indegrees of 0 and adds it to the queue and puts it in the output
//			for (int i = 0; i < theCellArr.length * theCellArr.length; i++) {
//				if (theIndegrees[i / theCellArr.length][i % theCellArr.length] == 0) {
//					q.add(i);
//					s.add(theCellArr[i / theCellArr.length][i % theCellArr.length]);
//					theIndegrees[i / theCellArr.length][i % theCellArr.length]--;
//				}
//			}
//		} while (!q.isEmpty());
//
//		if (s.size() != theCellArr.length * theCellArr.length) {
//			System.out.println("CYCLE FOUND!");
//			return null;
//		}
//		return s;
//	}
	
	public static Stack<Cell> sort(final Cell[][] theCellArr, final int[][] theIndegrees) {
		Stack<Cell> s = new Stack<>();
		Queue<Integer> q = new LinkedList<>();
		
		// Add cells with 0 indegrees to the queue
		for (int i = 0; i < theCellArr.length; i++) {
			for (int j = 0; j < theCellArr[i].length; j++) {
				if (theIndegrees[i][j] == 0) {
					q.add(i * theCellArr.length + j);
				}
			}
		}
		
		while (!q.isEmpty()) {
			int dq = q.poll();
			Cell c = theCellArr[dq / theCellArr.length][dq % theCellArr.length];
			
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
						q.add(row * theCellArr.length + col);
					}
				}
			}
			
			// Add the processed cell to the output stack
			s.add(c);
		}
		
		if (s.size() != theCellArr.length * theCellArr[0].length) {
			System.out.println("CYCLE FOUND!");
			return null;
		}
		
		return s;
	}
}
