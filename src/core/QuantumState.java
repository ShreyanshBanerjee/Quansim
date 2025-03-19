package quansim.core;

import quansim.core.ComplexNo;

public class QuantumState {
	ComplexNo[] state;

	public QuantumState(int size) {
		this.state = new ComplexNo[size];
	}

	public QuantumState(ComplexNo[] state) {
		this.state = state;
	}

	public static QuantumState QBit0() {
		return new QuantumState(
			new ComplexNo[] {
				new ComplexNo(1),
				new ComplexNo(0)
			}
		);
	}

	public static QuantumState QBit1() {
		return new QuantumState(
			new ComplexNo[] {
				new ComplexNo(0),
				new ComplexNo(1)
			}
		);
	}

	public void set(int index, ComplexNo val) {
		this.state[index] = val;
	}

	public ComplexNo get(int index) {
		return this.state[index];
	}

	public ComplexNo meanAmplitude() {
		ComplexNo sum = new ComplexNo(0);
		for (ComplexNo no : this.state) {
			sum = sum.add(no);
		}
		return sum.div(new ComplexNo(this.state.length, 0));
	}

	public void normalize() {
		double norm = 0;

		for (ComplexNo no : this.state) {
			norm += no.modSq();
		}

		norm = Math.sqrt(norm);

		for (int i=0; i<this.state.length; i++) {
			this.state[i] = this.state[i].div(new ComplexNo(norm));
		} 
	}

	public ComplexNo innerProduct(QuantumState second) {
		ComplexNo result = new ComplexNo(0);

		for (int i=0; i<this.state.length; i++) {
			result = result.add(this.state[i].conjugate().mult(second.state[i]));
		}

		return result;
	}

	public QuantumState[] decomposeState(int numQubits) {
		int size = this.state.length;
    	if (size != (1 << numQubits)) {
        	System.out.println("Error: QuantumState.decomposeState() cannot handle the inputted array. It must be composed of qbits in a superposition of 2 states.");
    	}

    	QuantumState[] result = new QuantumState[numQubits];

    	for (int q = 0; q < numQubits; q++) {
        	ComplexNo[] singleQubitState = new ComplexNo[2];
        
        	singleQubitState[0] = new ComplexNo(0, 0);
        	singleQubitState[1] = new ComplexNo(0, 0);

        	for (int i = 0; i < size; i++) {
            	int bit = (i >> q) & 1;
            	singleQubitState[bit] = singleQubitState[bit].add(this.state[i]);
        	}

        	result[q] = new QuantumState(singleQubitState);
        	result[q].normalize();
    	}

    	return result;
	}



	public double probability(int index) {
		return this.state[index].modSq();
	}

	public static QuantumState superposition(ComplexNo[] amplitudes) {
		int size = amplitudes.length;
		
		QuantumState result = new QuantumState(size);

		result.state = amplitudes;

		result.normalize();
		
		return result;
	}

	public static QuantumState tensorProduct(QuantumState state1, QuantumState state2) {
		int size1 = state1.state.length;
		int size2 = state2.state.length;

		QuantumState result = new QuantumState(size1 * size2);

		for (int a=0; a<size1; a++) {
			for (int b=0; b<size2; b++) {
				result.set(a * size2 + b, state1.get(a).mult(state2.get(b)));
			}
		}

		return result;
	}

	public void collapse(int index) {
		for (int i=0; i<this.state.length; i++) {
			if (i == index) {
				this.state[i] = new ComplexNo(1);
			} else {
				this.state[i] = new ComplexNo(0);
			}
		}
	}

	public int measure() {
		double random = Math.random();
		double cumulative = 0;

		for (int i=0; i<this.state.length; i++) {
			cumulative += this.probability(i);
			if (cumulative >= random) {
				this.collapse(i);
				return i;
			}
		}

		System.out.println("Error: when calling QuantumState.measure(), the sum of all the probabilities was < 1.");
		System.out.println("Perhaps you forgot to call QuantumState.normalize()?");
		return -1;
	}
}