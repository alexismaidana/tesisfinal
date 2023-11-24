$(document).ready( function () {
    $('#tablaProveedores').DataTable({
        order:[[3, "asc"]],
        lengthMenu: [2, 5, 10, 15, 20],
        columns: [
            null,
            {orderable: false},
            null,
            null,
            null,
            null,
            null,
                 
        ],
        language: {
            "search": "Buscar",
            "lengthMenu": "Mostrar _MENU_ registros",
            "info": "Mostrando de _START_ a _END_ de _TOTAL_ proveedores",
            "infoFiltered": "(Filtrado de _MAX_ de proveedores)",
            "emptyTable": "Todavía no hay Proveedores",
            "paginate": {
                "previous": "Anterior",
                "next": "Siguiente"
            }
        }
    });
    
});