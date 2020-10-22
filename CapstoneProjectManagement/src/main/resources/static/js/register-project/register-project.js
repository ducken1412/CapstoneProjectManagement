function getDataMember() {
    let ary = [];
    $('.attrTable tr').each(function (a, b) {
        let name = $('.attrName', b).text();
        let value = $('.attrValue', b).find(":selected").text();
        ary.push({username: name, role: value});

    });
    return ary;
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    indexed_array["members"] = getDataMember();
    return indexed_array;
}

$(document).on("submit", "#member", function (e) {
    
})

$(document).on("submit", "#register", function (e) {
    e.preventDefault();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $('#member').prop('disabled', true);
    let $form = $("#register");
    let dataForm = getFormData($form);
    $.ajax({
        url: "/register",
        type: "POST",
        data: JSON.stringify(dataForm),
        contentType: "application/json",
        success: function (data) {
            $("#form-content").html(data)
            let check = false
            $(".error-mess").each(function loop() {
                if ($(this).text() !== "") {
                    check = true
                }
            });
            if (!check) {
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