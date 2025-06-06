document.querySelectorAll('.view-btn').forEach(button => {
    button.addEventListener('click', function() {
        alert('Menampilkan soal untuk topik: ' + this.closest('tr').querySelector('td').textContent);
    });
});
