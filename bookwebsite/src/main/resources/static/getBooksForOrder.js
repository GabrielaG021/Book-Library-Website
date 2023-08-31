// Function to update the book dropdown with fetched book data
    function populateBooksDropdown(books) {
        const bookDropdown = document.getElementById('book');

        // Clear existing options
        bookDropdown.innerHTML = '<option value="">Select a book</option>';

        // Add fetched book data as options
        books.forEach(book => {
            const option = document.createElement('option');
            option.value = book.id;
            option.text = book.title;
            bookDropdown.appendChild(option);
        });
    }

    function calculateTotal() {
        const bookSelect = document.getElementById('book');
        const totalInput = document.getElementById('total');

        // Get the selected book's price from the value attribute (format: 'bookId - $price')
        const selectedPrice = parseFloat(bookSelect.options[bookSelect.selectedIndex].value.split(' - $')[1]);

        // Set the total input field to the selected book's price
        totalInput.value = selectedPrice.toFixed(2);

        // Generate and set the orderDate in the required format (yyyy-MM-ddTHH:mm:ss)
        const currentDate = new Date();
        const orderDate = currentDate.toISOString().slice(0, 19).replace('T', ' ');
        console.log(orderDate); // Add this line to print the date string in the console
        document.getElementById('date').value = orderDate;
    }

    // Function to calculate and update the total based on the selected book
    document.getElementById('book').addEventListener('change', function() {
        const selectedBookId = this.value;
        const selectedBook = books.find(book => book.id === parseInt(selectedBookId));
        const totalInput = document.getElementById('total');

        if (selectedBook) {
            totalInput.value = selectedBook.price;
        } else {
            totalInput.value = '';
        }
    });

    // Fetch book data from the server
    fetch('./books') // Replace with the actual API endpoint to fetch books
        .then(response => response.json())
        .then(data => {
            const books = data; // Assuming the API returns an array of book objects
            populateBooksDropdown(books);
        })
        .catch(error => console.error('Error fetching book data:', error));