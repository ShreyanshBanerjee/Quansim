package quansim.core;

import java.util.ArrayList;
import quansim.core.QuantumState;
import quansim.core.QuantumOperator;

public class QuantumCircuit {
	private final int register_size;
	private ArrayList<QuantumOperator> operations;
	private ArrayList<int[]> target_qbits;

	public QuantumCircuit(int register_size) {
		this.register_size = register_size;
		this.operations = new ArrayList<>();
		this.target_qbits = new ArrayList<>();
	}

	public final void apply(QuantumOperator q, int[] targets) {
		target_qbits.add(targets);
		operations.add(q);
	}

	public final void applyOnEachQbit(QuantumOperator q) {
		for (int i=0; i<this.register_size; i++) {
			this.operations.add(q);
			this.target_qbits.add( new int[] {i});
		}
	}

	public final void applyOnGlobalState(QuantumOperator q) {
		int[] qbits = new int[register_size];

		for (int i=0; i<this.register_size; i++) {
			qbits[i] = i;
		}

		this.apply(q, qbits);
	}

	public final QuantumState[] run_and_return_qstate() {
		QuantumState[] registerA = new QuantumState[register_size];

		for (int i=0; i<register_size; i++) {
			registerA[i] = QuantumState.QBit0();
		}

		for (int i=0; i<this.operations.size(); i++) {
			int[] current_targets = this.target_qbits.get(i);

			//summing up the qbits
			QuantumState result = registerA[current_targets[0]];
			for (int j=1; j<current_targets.length; j++) {
				result = QuantumState.tensorProduct(result, registerA[current_targets[j]]);
			}

			result = this.operations.get(i).apply(result);

			if (current_targets.length > 1) {
				QuantumState[] qbits = result.decomposeState(current_targets.length);

				for (int k=0; k<current_targets.length; k++) {
					registerA[current_targets[k]] = qbits[k];
				}
			} else {
				registerA[current_targets[0]] = result;
			}
		}

		return registerA;
	}

	public int[] run() {
		QuantumState[] quantum_bits = this.run_and_return_qstate();
		int[] result = new int[register_size];

		for (int i=0; i<register_size; i++) {
			result[i] = quantum_bits[i].measure();
		}

		return result;
	}

	public static int binaryListToBase10(int[] binary_nums) {
		int binary_result = 0;

		for (int i=0; i<binary_nums.length; i++) {
			binary_result += binary_nums[i] * Math.pow(2, binary_nums.length-i-1);
		}
		
		return binary_result;
	}
}
