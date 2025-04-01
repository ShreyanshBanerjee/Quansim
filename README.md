# Quansim, a Beginner-Friendly Quantum Computing Library

***NOTE: As this is merely a simulation of a quantum computer running on a classical computer, measurements of quantum states within the simulation aren't truly probabilistic but rather pseudo-random, so results may not exactly align with expectations*** 
## What are Quantum Computers
Quantum Computers are promising alternatives to the typical binary computers we use every day.
They use aspects of quantum physics to speed up computation rapidly.
### Superposition
Superposition is the primary ability which give quantum computers such an edge in performance in comparision to normal computers.
Small enough particles can be in multiple possible states. The state they are in is truly random, which greatly contrasts the determinism of
both classical and relativistic physics.  
A superposition can be modeled as a list of multiple complex numbers, each corresponding to a probability.
Let's say we have a quantum bit in superposition of being off or on. The current state of the system can be modeled as:  
#### [a<sub>1</sub>+b<sub>1</sub>i, a<sub>2</sub>+b<sub>2</sub>i],  
where index 0 relates to the probability of observing an off state and index 1 relates to the probability of observing the on state.
More specifically, the probability of observing state *n* is the modulus squared of the corresponding complex number
(given by evaluating a<sub>n</sub><sup>2</sup>+b<sub>n</sub><sup>2</sup>), and all the probabilities add up to 1.
Superposition is exploited in quantum computers to explore multiple different possibilities. For example, superposition can be used in Grover's
search algorithm to find a marked element much faster, having an O(N<sup>0.5</sup>) time complexity, much faster than classical counterparts.

### Quantum Gates
To operate on quantum bits - or qubits - special gates can be employed to leverage superposition. One example of a Quantum Gate is the Hadamard gate with the following matrix:  
`[1/√2, 0,   `      
` 0   , 1/√2]`  
 This gate puts an uninitialized gate (perhaps set to having a 100% chance of being 0) into a equal superposition of both 0 and 1, allowing for further gates to take advantage of superposition. Gates are applied onto qubits via the dot product.

## Usage
Using QuanSim is extremely simple. Create a new `QuantumCircuit` instance, apply operations on every qubit via the `.applyOnEachQubit(operator)` method, which takes in a QuantumOperator, and for operations on specific Qubits call the `.apply(operator, targets)`, which applies a specific operation on each qubit passed into the targets list. 
For running the produced QuantumCircuit, run either the `.run_and_return_qstate()` method for getting the entire quantum state, or simply the `.run()` method, which returns a list of the observed results. 

## Provided Examples
I have written two simple circuits in the examples folder for others' reference, experimentation, or for further understanding. The first circuit leverages superposition to generate a truly random number - which is not possible 
