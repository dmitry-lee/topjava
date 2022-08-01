const mealsAjaxUrl = "profile/meals/";

const ctx = {
    ajaxUrl: mealsAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealsAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

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
                    "render": renderDeleteBtn,
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

function clearFilter() {
    $("#filter")[0].reset();
    ctx.updateTable();
}