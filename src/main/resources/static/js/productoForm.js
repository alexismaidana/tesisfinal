$(document).ready(function() {
    // Cuando se cargue el documento
    let url = window.location.href;
    if (url.indexOf("/productos/editar/") !== -1) {
        return false
    };
    obtenerNuevoCodigo(); // Llama a la función para obtener el nuevo código
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
