function getDataMember() {
    let ary = [];
    $(function () {
        $('.attrTable tr').each(function (a, b) {
            let name = $('.attrName', b).text();
            let value = $('.attrValue', b).find(":selected").text();
            ary.push({ username: name, role: value });

        });
    });
    return ary;
}

$(document).on("submit", "#register", function (e) {
    e.preventDefault();
    // console.log(getDataMember())
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $('#member').prop('disabled', true);
    let dataForm = $("#register").serializeArray();
    dataForm.push({"members":getDataMember()});
    let members = getDataMember();
    console.log(dataForm)
    $.ajax({
        url: "/register/" + members,
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