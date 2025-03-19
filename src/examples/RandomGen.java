package quansim.examples;

import quansim.core.QuantumCircuit;
import quansim.core.QuantumOperator;
import quansim.core.QuantumAlgorithm;

public class RandomGen extends QuantumAlgorithm<Object, Double> {
	QuantumCircuit circuit;
	int qbits;

	public RandomGen(int noQbits) {
		this.circuit = new QuantumCircuit(noQbits);
		this.circuit.applyOnEachQbit(QuantumOperator.HADAMARD);
		this.qbits = noQbits;
	}

	public Double run(Object input) {
		return QuantumCircuit.binaryListToBase10(this.circuit.run()) / Math.pow(2, qbits); //the more qbits there are, the more fine-grained the result is
	}

    public static void main(String[] args) {
        RandomGen random_10qbits = new RandomGen(10);

        System.out.println("Random Number Generation via Quantum Superposition:");
        System.out.println("\tOutput (float 0-1): " + random_10qbits.run());
        System.out.println("\tRun again for new values.");
    }
}