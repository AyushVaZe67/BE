from flask import Flask, request, render_template_string

app = Flask(__name__)

comments = []

html_template = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Simple Blog - Stored XSS Demo</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            color: #333;
        }
        .container {
            width: 80%;
            max-width: 800px;
            margin: auto;
            background-color: #fff;
            padding: 20px;
            margin-top: 40px;
            border-radius: 12px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .container:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
        }
        h1, h2 {
            text-align: center;
            color: #555;
            font-size: 2.2rem;
            margin-bottom: 20px;
        }
        textarea {
            width: 100%;
            padding: 12px;
            border-radius: 8px;
            border: 1px solid #ddd;
            font-size: 16px;
            box-sizing: border-box;
            margin-bottom: 15px;
            transition: border-color 0.3s ease;
        }
        textarea:focus {
            border-color: #007bff;
            outline: none;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }
        input[type="submit"]:active {
            background-color: #004085;
        }
        ul {
            list-style-type: none;
            padding: 0;
            margin-top: 20px;
        }
        li {
            background-color: #e9ecef;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 10px;
            font-size: 16px;
            opacity: 0;
            animation: fadeIn 0.5s forwards;
        }
        li:nth-child(1) {
            animation-delay: 0.2s;
        }
        li:nth-child(2) {
            animation-delay: 0.4s;
        }
        li:nth-child(3) {
            animation-delay: 0.6s;
        }
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }
        .footer {
            text-align: center;
            font-size: 14px;
            color: #777;
            margin-top: 40px;
            padding-top: 20px;
        }
        @media (max-width: 768px) {
            .container {
                width: 90%;
            }
            h1 {
                font-size: 1.8rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📝 Leave a Comment</h1>
        <form method="POST" id="comment-form">
            <textarea name="comment" rows="4" placeholder="Your comment..."></textarea><br>
            <input type="submit" value="Submit">
        </form>

        <h2>💬 All Comments</h2>
        <ul id="comments-list">
            {% for comment in comments %}
                <li>{{ comment|safe }}</li>
            {% endfor %}
        </ul>

        <div class="footer">
            &copy; 2025 Simple Blog Demo | Made with ❤️
        </div>
    </div>

    <script>
        const form = document.getElementById('comment-form');
        form.addEventListener('submit', function(event) {
            setTimeout(() => {
                const commentInput = form.querySelector('textarea');
                commentInput.value = '';  // Clear comment input after submission
            }, 500);  // Delay the clearing to show the new comment

            const submitButton = form.querySelector('input[type="submit"]');
            submitButton.disabled = true; // Disable submit button to prevent multiple clicks

            setTimeout(() => {
                submitButton.disabled = false;
            }, 1000);  // Enable submit button after 1 second to allow another click
        });
    </script>
</body>
</html>
"""

@app.route("/", methods=["GET", "POST"])
def index():
    if request.method == "POST":
        comment = request.form.get("comment")
        comments.append(comment)  # Stored without sanitization (vulnerable to XSS)
    return render_template_string(html_template, comments=comments)

if __name__ == "__main__":
    app.run(debug=True)
