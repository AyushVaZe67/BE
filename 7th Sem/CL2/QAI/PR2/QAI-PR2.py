from qiskit import QuantumCircuit
from qiskit_aer import Aer

# Create a 3-qubit, 3-classical-bit circuit
qc = QuantumCircuit(3, 3)

# Step 1: Prepare state to teleport (|1>)
qc.x(0)

# Step 2: Create entanglement between qubit 1 and 2
qc.h(1)
qc.cx(1, 2)

# Step 3: Bell measurement on qubits 0 and 1
qc.cx(0, 1)
qc.h(0)
qc.measure([0, 1], [0, 1])

# Step 4: Apply conditional corrections (classic if-else style)
# Qiskit 1.x requires explicit 'if_test' with qubit arguments
with qc.if_test((qc.clbits[0], 1)):
    qc.x(2)
with qc.if_test((qc.clbits[1], 1)):
    qc.z(2)

# Step 5: Measure the teleported qubit
qc.measure(2, 2)

# Step 6: Simulate
backend = Aer.get_backend("qasm_simulator")
result = backend.run(qc, shots=1).result()
print("Teleportation Result:", result.get_counts())
