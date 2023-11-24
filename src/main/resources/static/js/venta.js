// BUSQUEDA DE VENTA
document.addEventListener("DOMContentLoaded", function () {
    const searchBar = document.getElementById("search-bar");
    const searchInput = searchBar.querySelector("input");
    const tableRows = document.querySelectorAll("tbody tr");

    searchInput.addEventListener("input", function () {
        const searchTerm = this.value.trim().toLowerCase();

        tableRows.forEach(function (row) {
            const articleName = row.cells[3].innerText.toLowerCase();
            const articleCode = row.cells[1].innerText.toLowerCase();
            const userName = row.cells[2].innerText.toLowerCase(); // Nueva línea para obtener el nombre de usuario
            const showRow =
                articleName.includes(searchTerm) ||
                articleCode.includes(searchTerm) ||
                userName.includes(searchTerm); // Agregar comparación del nombre de usuario
            row.style.display = showRow ? "" : "none";
        });
    });
});

  
  