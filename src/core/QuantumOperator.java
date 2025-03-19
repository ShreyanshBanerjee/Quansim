package quansim.core;

import quansim.core.ComplexNo;
import quansim.core.QuantumState;

public class QuantumOperator {
	public ComplexNo[][] matrix;

	public QuantumOperator(ComplexNo[][] init) {
		this.matrix = init;
		if (this.matrix.length != this.matrix[0].length) {
			System.out.println("Error: the argument passed into the QuantumOperator constructor must have shape n*n");
		}
	}

	public QuantumOperator(int size) {
		this.matrix = new ComplexNo[size][size];
	}

	public QuantumState apply(QuantumState q) {
		QuantumState result = new QuantumState(q.state.length);
		
		int n = q.state.length;

		if (this.matrix.length != n || this.matrix[0].length != n) {
			System.out.println("Warning: when using QuantumOperator.apply(), the matrix should have shape n*n and the shape should have size n.");
			System.out.println("The program will compile as long as state length < matrix width however unintended consequences may occur.");
		}
		
		for (int i=0; i<n; i++) {
			ComplexNo sum = new ComplexNo(0);
			
			for (int j=0; j<n; j++) {
				sum = sum.add(this.matrix[i][j].mult(q.state[j]));
			}
			result.state[i] = sum;
		}

		return result;
	}

	public QuantumOperator adjoint() {
		int n = this.matrix.length;

		QuantumOperator result = new QuantumOperator(n);

		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				result.matrix[j][i] = this.matrix[i][j].conjugate();
			}
		}

		return result;
	}

	public static QuantumOperator HADAMARD = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(1 / Math.sqrt(2)), new ComplexNo(1 / Math.sqrt(2))},
			{new ComplexNo(1 / Math.sqrt(2)), new ComplexNo(-1 / Math.sqrt(2))}
		}
	);

	public static QuantumOperator NOT = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(0), new ComplexNo(1)},
			{new ComplexNo(1), new ComplexNo(0)}
		}
	);

	public static QuantumOperator CNOT = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(1), new ComplexNo(0), new ComplexNo(0), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(1), new ComplexNo(0), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(0), new ComplexNo(0), new ComplexNo(1)},
			{new ComplexNo(0), new ComplexNo(0), new ComplexNo(1), new ComplexNo(0)}
		}
	);

	public static QuantumOperator CZ = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(1), new ComplexNo(0), new ComplexNo(0), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(1), new ComplexNo(0), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(0), new ComplexNo(1), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(0), new ComplexNo(0), new ComplexNo(-1)}
		}
	);

	public static QuantumOperator SWAP = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(0), new ComplexNo(0), new ComplexNo(0), new ComplexNo(1)},
			{new ComplexNo(0), new ComplexNo(0), new ComplexNo(1), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(1), new ComplexNo(0), new ComplexNo(0)},
			{new ComplexNo(1), new ComplexNo(0), new ComplexNo(0), new ComplexNo(0)}
		}
	);


	public static QuantumOperator PHASE = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(1), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(0, 1)}
		}
	);


	public static QuantumOperator T = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(1), new ComplexNo(0)},
			{new ComplexNo(0), ComplexNo.fromExp(Math.PI / 4)}
		}
	);

	public static QuantumOperator Z = new QuantumOperator(
		new ComplexNo[][] {
			{new ComplexNo(1), new ComplexNo(0)},
			{new ComplexNo(0), new ComplexNo(-1)}
		}
	);

	public static QuantumOperator CPHASE(double theta) {
		return new QuantumOperator(
			new ComplexNo[][] {
				{new ComplexNo(1), new ComplexNo(0), new ComplexNo(0), new ComplexNo(0)},
				{new ComplexNo(0), new ComplexNo(1), new ComplexNo(0), new ComplexNo(0)},
				{new ComplexNo(0), new ComplexNo(0), new ComplexNo(1), new ComplexNo(0)},
				{new ComplexNo(0), new ComplexNo(0), new ComplexNo(0), new ComplexNo(0, 1)}
			}
		);
	}
}