from qiskit import QuantumCircuit
from qiskit_aer import Aer
import matplotlib.pyplot as plt

# Number of qubits
n = 16

# Create 16-qubit circuit
qc = QuantumCircuit(n, n)
qc.h(range(n))             # Apply Hadamard gate to each qubit (create superposition)
qc.measure(range(n), range(n))  # Measure all qubits

# Draw the circuit
print(qc.draw(output="text"))

# Use QASM simulator
backend = Aer.get_backend("qasm_simulator")

# Number of measurements (shots)
shots = 10

# Run the circuit on simulator
job = backend.run(qc, shots=shots)
result = job.result()

# Get measurement results
counts = result.get_counts()

print("\nRandom 16-bit outcomes:")
for bitstring in counts:
    print(bitstring)
