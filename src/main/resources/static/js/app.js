document.addEventListener("DOMContentLoaded", function() {
    const apiUrl = 'http://localhost:8080';

    document.getElementById('createForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = {
            artist: document.getElementById('artist').value,
            date: document.getElementById('date').value,
            title: document.getElementById('title').value,
            art: document.getElementById('art').value
        };

        fetch(`${apiUrl}/create-ascii`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            alert('ASCII-konst skapad: ' + data.title);
            // Clear form after success
            document.getElementById('createForm').reset();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Fel vid skapande av ASCII-konst');
        });
    });

    document.getElementById('searchTitleForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const title = document.getElementById('searchTitle').value;
        fetch(`${apiUrl}/search-by-title?title=${title}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('searchResult').innerText = data.art || 'Ingen konst hittades';
            })
            .catch(error => {
                document.getElementById('searchResult').innerText = 'Ingen konst hittades';
                console.error('Error:', error);
            });
    });

    document.getElementById('deleteForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const title = document.getElementById('deleteTitle').value;
        fetch(`${apiUrl}/${title}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            alert('Konstverket "' + title + '" är raderat');
            // Clear form after success
            document.getElementById('deleteForm').reset();
        })
        .catch(error => {
            alert('Fel vid borttagning av ASCII-konst');
            console.error('Error:', error);
        });
    });

    function viewAllAscii() {
        fetch(`${apiUrl}/view-asciis`)
            .then(response => response.json())
            .then(data => {
                const asciiList = document.getElementById('asciiList');
                asciiList.innerHTML = '';
                data.forEach(ascii => {
                    const div = document.createElement('div');
                    div.innerHTML = `<h3>${ascii.title} (${ascii.artist})</h3><pre>${ascii.art}</pre>`;
                    asciiList.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    viewAllAscii();
});