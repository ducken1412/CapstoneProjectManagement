$(document).on("change", "#drd-week", function (e) {
    $('#tasks-container').LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    const projectId = $('#projectId').val();
    const week = this.value;
    $.ajax({
        url: "/db/task-detail/" + projectId,
        type: "POST",
        data: {
            'week' : week
        },
        success: function (data) {
            $('#tasks-container').html(data);
            $('#tasks-container').LoadingOverlay("hide");
        },
        error: function (xhr) {
            if (xhr.status == 302 || xhr.status == 200) {
                // window.location.href = "/forum";
            }
        },
    });
})