const BASE_URL = "https://blockbox-backend.onrender.com";

const uploadForm = document.getElementById("uploadForm");
const fileInput = document.getElementById("fileInput");
const messageDiv = document.getElementById("message");
const fileList = document.getElementById("fileList");

uploadForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch(`${BASE_URL}/files/upload`, {
        method: "POST",
        body: formData
    });

    if (response.ok) {
        messageDiv.textContent = "✅ File uploaded!";
        fileInput.value = "";
        loadFiles();
    } else {
        messageDiv.textContent = "❌ Upload failed.";
    }
});

async function loadFiles() {
    const res = await fetch(`${BASE_URL}/files`);
    const files = await res.json();
    fileList.innerHTML = "";
    files.forEach(file => {
        const li = document.createElement("li");
        li.innerHTML = `<a href="${BASE_URL}/files/download/${file.id}" download>${file.originalName}</a>`;
        fileList.appendChild(li);
    });
}

loadFiles();
