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

$(document).ready(function() {
    // Cuando se cargue el documento
    let url = window.location.href;
    if (url.indexOf("/productos/editar/") !== -1) {
        return false
    };
    obtenerNuevoCodigo(); 
});

function obtenerNuevoCodigo() {
    $.ajax({
        url: '/productos/generarCodigo', // La URL del controlador que genera el código
        type: 'GET',
        success: function(data) {
            // Cuando la solicitud AJAX sea exitosa, actualiza el campo de entrada con el nuevo código
            $('#codigoProducto').val(data);
        },
        error: function() {
            // Maneja los errores si es necesario
            console.error('Error al obtener el nuevo código de producto');
        }
    });
}