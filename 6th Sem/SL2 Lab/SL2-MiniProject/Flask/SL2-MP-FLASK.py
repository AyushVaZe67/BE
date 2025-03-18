from flask import Flask, request, jsonify
import cv2
import numpy as np
import pickle
import os

app = Flask(__name__)

# Load your exported model
with open('model.pkl', 'rb') as f:
    model = pickle.load(f)

# Dummy detect function (replace with your real logic)
def detect_mask_from_frame(frame, model):
    # Process frame and predict using model
    # Example placeholder return:
    return "Mask Detected"  # Replace with actual logic

# Endpoint to receive image and predict
@app.route('/predict', methods=['POST'])
def predict():
    if 'image' not in request.files:
        return jsonify({'error': 'No image uploaded'}), 400

    file = request.files['image']

    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    # Read image file as OpenCV image
    file_bytes = np.frombuffer(file.read(), np.uint8)
    frame = cv2.imdecode(file_bytes, cv2.IMREAD_COLOR)

    # Pass frame to your function
    result = detect_mask_from_frame(frame, model)

    return jsonify({'prediction': result}), 200

if __name__ == '__main__':
    app.run(debug=True)
