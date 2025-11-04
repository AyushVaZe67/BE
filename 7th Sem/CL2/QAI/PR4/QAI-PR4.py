from qiskit import QuantumCircuit
from qiskit_aer import Aer
from qiskit.visualization import plot_histogram

# Step 1: Create 3-qubit + 3-classical-bit circuit
qc = QuantumCircuit(3, 3)

# Step 2: Encode logical |1> as |111>
qc.x(0)
qc.cx(0, 1)
qc.cx(0, 2)

# Step 3: Introduce a bit-flip error (simulate noise)
qc.x(1)  # flip middle qubit artificially

# Step 4: Decode (majority vote style correction)
qc.cx(0, 1)
qc.cx(0, 2)
qc.ccx(1, 2, 0)

# Step 5: Measure
qc.measure([0, 1, 2], [0, 1, 2])

print(qc.draw(output='text'))

# Step 6: Run simulation
backend = Aer.get_backend('qasm_simulator')
result = backend.run(qc, shots=1024).result()
counts = result.get_counts()

print("\nMeasurement outcomes:")
print(counts)
