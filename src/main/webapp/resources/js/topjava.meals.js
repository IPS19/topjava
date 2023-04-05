const mealAjaxUrl = "ui/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

function filterForm() {
    $.ajax({
        type: "GET",
        url: "ui/meals/filter/",
        data: $('#filter').serialize()
    }).done(function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
        successNoty("filtered");
    });
}

function resetFilter() {
    $("#filter")[0].reset();
    updateTable();
}

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});