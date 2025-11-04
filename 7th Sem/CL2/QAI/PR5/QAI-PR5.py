# Simple Quantum Random Computation with 3-Puzzle Problem
# Author: Ayush Vaze

from qiskit import QuantumCircuit
from qiskit_aer import Aer

# Quantum random number generator (2 qubits → 0–3)
def quantum_random_number():
    qc = QuantumCircuit(2, 2)
    qc.h([0, 1])  # superposition
    qc.measure([0, 1], [0, 1])

    sim = Aer.get_backend('qasm_simulator')
    result = sim.run(qc, shots=1).result()
    counts = result.get_counts()
    bitstring = list(counts.keys())[0]
    return int(bitstring, 2)

# 3-Puzzle setup (0 = blank)
puzzle = [1, 2, 3,
          4, 0, 5,
          6, 7, 8]

# Simple random move based on quantum randomness
moves = ["Up", "Down", "Left", "Right"]

print("Initial Puzzle State:", puzzle)
for i in range(5):  # make 5 random quantum moves
    move = moves[quantum_random_number() % 4]
    print(f"Quantum Move {i+1}: {move}")
print("Quantum Computation Complete ✅")
