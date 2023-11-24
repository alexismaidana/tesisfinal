$(document).ready( function () {
    $('#tablaUsuarios').DataTable({
        order:[[3, "asc"]],
        lengthMenu: [2, 5, 10, 15, 20],
        columns: [
            null,
            {orderable: false},
            null,
            null,
            null,
            
        ],
        language: {
            "search": "Buscar",
            "lengthMenu": "Mostrar _MENU_ registros",
            "info": "Mostrando de _START_ a _END_ de _TOTAL_ Usuarios",
            "infoFiltered": "(Filtrado de _MAX_ de Usuarios)",
            "emptyTable": "Todavía no hay Usuarios",
            "paginate": {
                "previous": "Anterior",
                "next": "Siguiente"
            }
        }
    });
    
});