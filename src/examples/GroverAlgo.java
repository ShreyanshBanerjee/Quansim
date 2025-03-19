package quansim.examples;

import quansim.core.QuantumCircuit;
import quansim.core.QuantumAlgorithm;
import quansim.core.QuantumOperator;
import quansim.core.ComplexNo;

public class GroverAlgo extends QuantumAlgorithm<Object, Integer> {
	QuantumCircuit circuit;
	QuantumOperator oracle;

	public GroverAlgo(QuantumOperator oracle) {
        //the construction of the quantum circuit should happen here, not in the run function

		if ((int) (Math.log(oracle.matrix.length) / Math.log(2)) != Math.log(oracle.matrix.length) / Math.log(2)) {
			System.out.println("Error: GroverAlgo is not intended to work with oracles that are not a power of 2.");
		}

		this.oracle = oracle;

		int numQbits = (int) (Math.log(this.oracle.matrix.length) / Math.log(2));

		QuantumOperator MCZ = GroverAlgo.buildOracle(this.oracle.matrix.length, this.oracle.matrix.length - 1);

		//circuit
		this.circuit = new QuantumCircuit(numQbits);

		this.circuit.applyOnEachQbit(QuantumOperator.HADAMARD);

		for (int i=0; i<Math.round(Math.sqrt(this.oracle.matrix.length) * Math.PI / 4); i++) {

			this.circuit.applyOnGlobalState(this.oracle); //application of oracle

			//diffusion:
			this.circuit.applyOnEachQbit(QuantumOperator.NOT);

			this.circuit.applyOnGlobalState(MCZ);

			this.circuit.applyOnEachQbit(QuantumOperator.NOT);
		}
	}

	public static QuantumOperator buildOracle(int size, int marked_item_index) {
		QuantumOperator result = new QuantumOperator(size);

		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				if (i==j && i==marked_item_index) {
					result.matrix[i][j] = new ComplexNo(-1);
				} else if (i==j) {
					result.matrix[i][j] = new ComplexNo(1);
				} else {
					result.matrix[i][j] = new ComplexNo(0);
				}
			}
		}

		return result;
	}

	public Integer run(Object input) {
        //use Object for the input type if you want the run function to not have any arguments, and simply call it with run()
		return this.oracle.matrix.length - 1 - QuantumCircuit.binaryListToBase10(this.circuit.run());
	}

    public static void main(String[] args) {
		int marked_index = (int)(Math.random() * 8);

        QuantumOperator oracle = GroverAlgo.buildOracle(8, marked_index);

        GroverAlgo grover_4i_m3 = new GroverAlgo(oracle); //an instance of the grover algorithm taking in 4 items and with the marked object at index 3

        System.out.println("Grover's Algorithm Circuit - Searching through a list length 8.");
        System.out.println("Marked Item Location: index " + marked_index);
        System.out.println("Grover Algorithm Output: index " + grover_4i_m3.run());
        System.out.println("Due to the probabilistic nature of quantum mechanics, values may be slightly inaccurate");
    }
}