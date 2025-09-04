import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from nltk.stem import PorterStemmer

nltk.download('punkt')
nltk.download('stopwords')

text = """Powerlifting focuses on three main lifts: squat, bench press, and deadlift.
Strength training in the gym helps build muscle and endurance.
Many athletes follow strict diet plans to improve performance.
Consistency and progressive overload are key for gaining strength.
Fitness is not just physical but also mental discipline."""

tokens = word_tokenize(text)

stop_words = set(stopwords.words('english'))
filtered_tokens = [word for word in tokens if word.lower() not in stop_words and word.isalpha()]

stemmer = PorterStemmer()
stemmed_tokens = [stemmer.stem(word) for word in filtered_tokens]

print("Original Text:\n", text)
print("\nTokens:\n", tokens)
print("\nAfter Stop Word Removal:\n", filtered_tokens)
print("\nAfter Stemming:\n", stemmed_tokens)
