from qiskit import QuantumCircuit
from qiskit_aer import Aer
import numpy as np

n = 5  # number of qubits
qc = QuantumCircuit(n)

# Quantum Fourier Transform
for j in range(n):
    qc.h(j)
    for k in range(j + 1, n):
        qc.cp(np.pi / (2 ** (k - j)), k, j)
# Swap qubits for bit reversal
for i in range(n // 2):
    qc.swap(i, n - i - 1)

# Show circuit
print(qc.draw(output='text'))

# Run the circuit on statevector simulator
backend = Aer.get_backend('statevector_simulator')
job = backend.run(qc)
result = job.result()
state = result.get_statevector()

print("\nFinal Quantum State:")
print(state)
