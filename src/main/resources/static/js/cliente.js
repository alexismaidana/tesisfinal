$(document).ready( function () {
    $('#tablaClientes').DataTable({
        order:[[3, "asc"]],
        lengthMenu: [2, 5, 10, 15, 20],
        columns: [
            null,
            {orderable: false},
            null,
            null,
            null,
            null,
            
                 
        ],
        language: {
            "search": "Buscar",
            "lengthMenu": "Mostrar _MENU_ registros",
            "info": "Mostrando de _START_ a _END_ de _TOTAL_ Clientes",
            "infoFiltered": "(Filtrado de _MAX_ de Clientes)",
            "emptyTable": "Todavía no hay Clientes",
            "paginate": {
                "previous": "Anterior",
                "next": "Siguiente"
            }
        }
    });
    
});
const $btnExportar = document.querySelector('#btnExportar'),
            $tablaClientes = document.querySelector('#tablaClientes');

        $btnExportar.addEventListener("click", function () {
            let tableExport = new TableExport($tablaClientes, {
                exportButtons: false, // No queremos botones
                filename: "Reporte de prueba", //Nombre del archivo de Excel
                sheetname: "Reporte de prueba", //Título de la hoja
            });
            let datos = tableExport.getExportData();
            let preferenciasDocumento = datos.tablaClientes.xlsx;
            tableExport.export2file(preferenciasDocumento.data, preferenciasDocumento.mimeType, preferenciasDocumento.filename, preferenciasDocumento.fileExtension, preferenciasDocumento.merges, preferenciasDocumento.RTL, preferenciasDocumento.sheetname);
        });


