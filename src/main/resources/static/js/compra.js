// BUSQUEDA DE COMPRA
document.addEventListener("DOMContentLoaded", function () {
    const searchBar = document.getElementById("search-bar");
    const searchInput = searchBar.querySelector("input");
    const tableRows = document.querySelectorAll("tbody tr");

    searchInput.addEventListener("input", function () {
        const searchTerm = this.value.trim().toLowerCase();

        tableRows.forEach(function (row) {
            const purchaseNumber = row.cells[1].innerText.toLowerCase(); // Número de compra
            const userName = row.cells[2].innerText.toLowerCase(); // Nombre de usuario
            const showRow =
                purchaseNumber.includes(searchTerm) || // Buscar por número de compra
                userName.includes(searchTerm); // Buscar por nombre de usuario
            row.style.display = showRow ? "" : "none";
        });
    });
});


  
  