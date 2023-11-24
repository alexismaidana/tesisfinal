$(document).ready( function () {
    $('#tablaProductos').DataTable({
        order:[[3, "asc"]],
        lengthMenu: [5, 10, 15, 20],
        columns: [
            null,
            {orderable: false},
            null,
            null,
            null,
            {bSearchable: false},
            {orderable: false}
        ],
        language: {
            "search": "Buscar",
            "lengthMenu": "Mostrar _MENU_ registros",
            "info": "Mostrando de _START_ a _END_ de _TOTAL_ Productos",
            "infoFiltered": "(Filtrado de _MAX_ de Productos)",
            "emptyTable": "Todavía no hay Productos",
            "paginate": {
                "previous": "Anterior",
                "next": "Siguiente"
            }
        }
     });
    
});

