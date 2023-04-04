const mealAjaxUrl = "ui/meals/";

const mealAjaxUrlFilter = "ui/meals/filter/"

const ctx = {
    ajaxUrl: mealAjaxUrl
};

const ctxf = {
    ajaxUrlFilter: mealAjaxUrlFilter
};

function filterForm() {
    form = $('#filter');
    $.ajax({
        type: "GET",
        url: ctxf.ajaxUrlFilter,
        data: form.serialize()
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
                    "asc"
                ]
            ]
        })
    );
});