$(document).on("submit", "#register", function (e) {
    e.preventDefault();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    let dataForm = $("#register").serialize();
    $.ajax({
        url: "/register",
        type: "POST",
        data: dataForm,
        success: function (data) {
            $("#form-content").html(data)
            let check = false
            $(".error-mess").each(function loop() {
                if($(this).text() !== ""){
                    check = true
                }
            });
            if(!check) {
                $.ajax({
                    url: "/get-member-form",
                    type: "GET",
                    data: dataForm,
                    success: function (data1) {
                        $("#modal-content").html(data1);
                        $('#member-container').modal('show');
                        $.LoadingOverlay("hide");

                    },
                    error: function (xhr) {
                        $("#modal-content").html("Error");
                    },
                });
            }
        },
        error: function (xhr) {
            console.log('as');
            $("#modal-content").html("Error");
        },
    });
});