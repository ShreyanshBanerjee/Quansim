package quansim.core;

import java.util.HashMap;
import java.util.Map;

public abstract class QuantumAlgorithm<I, O> {
	public abstract O run(I input);

	public O run() {
		return run(null);
	}

    public O runMultiple(int amt, I input) {
        Map<O, Integer> frequency = new HashMap<>();
    
        for (int i = 0; i < amt; i++) {
            O result = run(input);
            frequency.put(result, frequency.getOrDefault(result, 0) + 1);
        }

        return frequency.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .get()
            .getKey();  // Return most common result
        }

    public O runMultiple(int amt) {
        return runMultiple(amt, null);
    }
}