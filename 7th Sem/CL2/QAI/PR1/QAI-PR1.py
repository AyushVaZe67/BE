from qiskit import QuantumCircuit
from qiskit_aer import Aer
import matplotlib.pyplot as plt

n = 16
qc = QuantumCircuit(n, n)
qc.h(range(n))

qc.measure(range(n), range(n))

print(qc.draw(output="text"))

backend = Aer.get_backend("qasm_simulator")
shots = 10
result = backend.run(qc, shots=shots).result()
counts = result.get_counts()

print("\nRandom 16-bit outcomes:")
for bitstring in counts:
    print(bitstring)