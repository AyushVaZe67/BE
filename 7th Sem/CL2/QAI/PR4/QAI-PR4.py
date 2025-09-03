from qiskit import QuantumCircuit
from qiskit_aer import Aer

# --- Step 1: Create circuit with 3 qubits and 2 classical bits ---
qc = QuantumCircuit(3, 2)

# --- Step 2: Prepare Alice’s state |ψ> ---
qc.ry(0.6, 0)
qc.rz(0.8, 0)

# --- Step 3: Create entanglement between qubit 1 (Alice) and qubit 2 (Bob) ---
qc.h(1)
qc.cx(1, 2)

# --- Step 4: Bell measurement on Alice’s qubits ---
qc.cx(0, 1)
qc.h(0)
qc.measure(0, 0)
qc.measure(1, 1)

# --- Step 5: Bob’s conditional corrections ---
# if classical bit[1] == 1 → apply X
qc.if_test((qc.clbits[1], 1), qc.x(2))
# if classical bit[0] == 1 → apply Z
qc.if_test((qc.clbits[0], 1), qc.z(2))

print(qc.draw(output="text"))

# --- Step 6: Simulate ---
backend = Aer.get_backend("statevector_simulator")
result = backend.run(qc).result()
state = result.get_statevector()

print("\nFinal statevector:")
print(state)
