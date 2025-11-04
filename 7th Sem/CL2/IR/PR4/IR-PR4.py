import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import MultinomialNB
from sklearn.metrics import accuracy_score, confusion_matrix, classification_report

# Step 1: Load dataset (example: SMS Spam Collection dataset)
# Download from: https://archive.ics.uci.edu/ml/datasets/sms+spam+collection
# Dataset has two columns: 'label' (ham/spam), 'message'
data = pd.read_csv("spam_ham_dataset.csv", encoding='latin-1')[['v1', 'v2']]
data.columns = ['label', 'message']

# Step 2: Preprocess labels
data['label'] = data['label'].map({'ham': 0, 'spam': 1})  # ham=0, spam=1

# Step 3: Train-test split
X_train, X_test, y_train, y_test = train_test_split(
    data['message'], data['label'], test_size=0.2, random_state=42
)

# Step 4: Feature extraction (TF-IDF)
vectorizer = TfidfVectorizer(stop_words='english', max_df=0.7)
X_train_tfidf = vectorizer.fit_transform(X_train)
X_test_tfidf = vectorizer.transform(X_test)

# Step 5: Train model (Naive Bayes)
model = MultinomialNB()
model.fit(X_train_tfidf, y_train)

# Step 6: Predictions
y_pred = model.predict(X_test_tfidf)

# Step 7: Evaluation
print("✅ Accuracy:", accuracy_score(y_test, y_pred))
print("\nConfusion Matrix:\n", confusion_matrix(y_test, y_pred))
print("\nClassification Report:\n", classification_report(y_test, y_pred))

# Step 8: Test with new emails
emails = [
    "Congratulations! You won a $500 gift card. Click here to claim now!",
    "Hey John, are we still meeting at the gym tomorrow?",
    "Reminder: Your electricity bill is due tomorrow. Pay securely online."
]

emails_tfidf = vectorizer.transform(emails)
predictions = model.predict(emails_tfidf)

for email, label in zip(emails, predictions):
    print(f"\nEmail: {email}\nPrediction: {'Spam' if label==1 else 'Ham'}")
