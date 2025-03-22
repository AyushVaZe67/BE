from flask import Flask, render_template, request
import numpy as np
import cv2
from tensorflow.keras.models import load_model
from tensorflow.keras.applications.vgg16 import preprocess_input
from PIL import Image
import io
import base64

app = Flask(__name__)

# Load the Keras model once when the server starts
model = load_model('model1.h5')  # Your model path

# Helper: Convert image to base64 for displaying in HTML
def image_to_base64(image_pil):
    buffer = io.BytesIO()
    image_pil.save(buffer, format="JPEG")
    buffer.seek(0)
    img_str = base64.b64encode(buffer.read()).decode()
    return img_str

@app.route('/', methods=['GET', 'POST'])
def index():
    prediction = None
    confidence = None
    img_data = None

    if request.method == 'POST':
        file = request.files['image']

        if file:
            # Read image and convert to PIL
            image_pil = Image.open(file).convert("RGB")
            img_data = image_to_base64(image_pil)  # For displaying in HTML

            # Convert PIL to OpenCV format
            frame = np.array(image_pil)
            frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)

            # Preprocess for model
            img = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            img_resized = cv2.resize(img, (224, 224)).astype(np.float32)

            # Handle grayscale (if needed)
            if len(img_resized.shape) == 2:
                img_resized = cv2.cvtColor(img_resized, cv2.COLOR_GRAY2RGB)

            # Preprocess for VGG16
            img_preprocessed = preprocess_input(img_resized)
            img_preprocessed = img_preprocessed.reshape(1, 224, 224, 3)

            # Predict
            y_pred_prob = model.predict(img_preprocessed)[0][0]
            y_class = 0 if y_pred_prob <= 0.5 else 1

            class_mapping = {0: "With Mask", 1: "Without Mask"}
            prediction = class_mapping[y_class]
            confidence = y_pred_prob * 100 if y_class == 1 else (1 - y_pred_prob) * 100

    return render_template('index.html', prediction=prediction, confidence=confidence, img_data=img_data)

if __name__ == '__main__':
    app.run(debug=True)
