<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Share with a Friend</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .share-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            padding: 30px;
            transition: all 0.3s ease;
        }

        .share-container:hover {
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.15);
        }

        .share-header {
            text-align: center;
            margin-bottom: 25px;
            color: #2c3e50;
        }

        .share-header i {
            background: linear-gradient(135deg, #3498db, #8e44ad);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            font-size: 2.8rem;
            margin-bottom: 15px;
        }

        .share-header h2 {
            font-weight: 700;
            margin-bottom: 5px;
            font-size: 1.8rem;
        }

        .share-header p {
            color: #7f8c8d;
            font-size: 1.1rem;
        }

        .form-label {
            font-weight: 600;
            color: #34495e;
            margin-bottom: 8px;
        }

        .form-control {
            padding: 14px 18px;
            border-radius: 10px;
            border: 2px solid #e0e6ed;
            transition: all 0.3s;
            font-size: 1rem;
        }

        .form-control:focus {
            border-color: #3498db;
            box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.25);
        }

        .btn-send {
            background: linear-gradient(135deg, #3498db, #8e44ad);
            border: none;
            padding: 12px 30px;
            font-weight: 600;
            font-size: 1.1rem;
            border-radius: 10px;
            transition: all 0.3s ease;
            width: 100%;
            box-shadow: 0 4px 15px rgba(52, 152, 219, 0.3);
        }

        .btn-send:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(52, 152, 219, 0.4);
        }

        .btn-send:active {
            transform: translateY(1px);
        }

        .validation-message {
            font-size: 0.9rem;
            color: #e74c3c;
            margin-top: 8px;
            height: 20px;
        }
    </style>
</head>
<body>
<div class="share-container">
    <div class="share-header">
        <h2>Send video to your friend</h2>
        <p>Share this amazing content with someone special</p>
    </div>

    <form id="shareForm">
        <div class="mb-4">
            <label for="friendEmail" class="form-label">Your friend's email?</label>
            <input
                    type="email"
                    class="form-control"
                    id="friendEmail"
                    placeholder="name@example.com"
                    required
            >
            <div class="validation-message" id="emailValidation"></div>
        </div>

        <button type="submit" class="btn btn-send">
            <i class="fas fa-paper-plane me-2"></i>Send
        </button>
    </form>
</div>

<script>
    let videoTitle = '';
    let youtubeLink = '';
    // Lấy videoId từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const videoId = urlParams.get('id');
    // Fetch video info
    if (videoId) {
        fetch(window.location.origin + '/PolySys_war/video/api/detail?id=' + videoId)
            .then(res => res.json())
            .then(video => {
                videoTitle = video.title;
                youtubeLink = video.link;
            });
    }

    document.getElementById('shareForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        // Validate email input
        const emailInput = document.getElementById('friendEmail');
        const validationMessage = document.getElementById('emailValidation');
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(emailInput.value)) {
            validationMessage.textContent = 'Please enter a valid email address';
            emailInput.classList.add('is-invalid');
            return;
        }
        validationMessage.textContent = '';
        emailInput.classList.remove('is-invalid');
        // Gửi email qua API
        const res = await fetch(window.location.origin + '/PolySys_war/video/api/share', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: 'recipientEmail=' + encodeURIComponent(emailInput.value) +
                  '&videoTitle=' + encodeURIComponent(videoTitle) +
                  '&youtubeLink=' + encodeURIComponent(youtubeLink)
        });
        const result = await res.json();
        if (result.success) {
            emailInput.value = '';
            const originalText = document.querySelector('.btn-send').innerHTML;
            document.querySelector('.btn-send').innerHTML = '<i class="fas fa-check me-2"></i>Sent!';
            setTimeout(() => {
                document.querySelector('.btn-send').innerHTML = originalText;
            }, 2000);
        } else {
            validationMessage.textContent = result.error || 'Send failed!';
        }
    });

    // Add real-time validation
    document.getElementById('friendEmail').addEventListener('input', function() {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const validationMessage = document.getElementById('emailValidation');

        if (this.value === '') {
            validationMessage.textContent = '';
            this.classList.remove('is-invalid');
        } else if (!emailRegex.test(this.value)) {
            validationMessage.textContent = 'Please enter a valid email address';
            this.classList.add('is-invalid');
        } else {
            validationMessage.textContent = '';
            this.classList.remove('is-invalid');
        }
    });
</script>
</body>
</html>
