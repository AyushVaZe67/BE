from flask import Flask, request, render_template
import numpy as np
import cv2
from keras.models import load_model
from keras.applications.vgg16 import preprocess_input
from PIL import Image
import os

app = Flask(__name__)
model = load_model('model1.h5')  # Path to your .h5 model

UPLOAD_FOLDER = 'static/uploads'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER

def detect_mask_from_frame(frame, model):
    img = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
    img_resized = cv2.resize(img, (224, 224)).astype(np.float32)

    if len(img_resized.shape) == 2:
        img_resized = cv2.cvtColor(img_resized, cv2.COLOR_GRAY2RGB)

    img_resized = preprocess_input(img_resized)
    img_resized = img_resized.reshape(1, 224, 224, 3)

    y_pred_prob = model.predict(img_resized)[0][0]
    y_pred = 0 if y_pred_prob <= 0.5 else 1

    class_mapping = {0: "With Mask", 1: "Without Mask"}
    confidence = y_pred_prob * 100 if y_pred == 1 else (1 - y_pred_prob) * 100

    return class_mapping[y_pred], round(confidence, 2)

@app.route('/', methods=['GET', 'POST'])
def index():
    prediction = None
    confidence = None
    filename = None

    if request.method == 'POST':
        file = request.files['image']
        if file:
            filename = os.path.join(app.config['UPLOAD_FOLDER'], file.filename)
            file.save(filename)

            image_pil = Image.open(filename).convert("RGB")
            frame = np.array(image_pil)
            frame = cv2.cvtColor(frame, cv2.COLOR_RGB2BGR)

            prediction, confidence = detect_mask_from_frame(frame, model)

    return render_template('index.html',
                           prediction=prediction,
                           confidence=confidence,
                           filename=filename)

if __name__ == '__main__':
    app.run(debug=True)
