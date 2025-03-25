from flask import Flask, request, jsonify
import numpy as np
import cv2
from tensorflow.keras.models import load_model
from tensorflow.keras.applications.vgg16 import preprocess_input
from PIL import Image
import io

app = Flask(__name__)

# Load the Keras model once when the server starts
model = load_model('model1.h5')  # Your model path

@app.route('/', methods=['GET'])
def home():
    return jsonify({"message": "Welcome to the Mask Detection API. Use POST /predict with an image."})

@app.route('/predict', methods=['POST'])
def predict():
    if 'image' not in request.files:
        return jsonify({"error": "No image provided"}), 400

    file = request.files['image']

    try:
        # Read and process image
        image_pil = Image.open(file).convert("RGB")
        frame = np.array(image_pil)
        frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)

        img = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        img_resized = cv2.resize(img, (224, 224)).astype(np.float32)

        if len(img_resized.shape) == 2:
            img_resized = cv2.cvtColor(img_resized, cv2.COLOR_GRAY2RGB)

        img_preprocessed = preprocess_input(img_resized)
        img_preprocessed = img_preprocessed.reshape(1, 224, 224, 3)

        # Predict
        y_pred_prob = model.predict(img_preprocessed)[0][0]
        y_class = 0 if y_pred_prob <= 0.5 else 1

        class_mapping = {0: "With Mask", 1: "Without Mask"}
        prediction = class_mapping[y_class]
        confidence = y_pred_prob * 100 if y_class == 1 else (1 - y_pred_prob) * 100

        return jsonify({
            "prediction": prediction,
            "confidence": float(round(confidence, 2))
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True)
