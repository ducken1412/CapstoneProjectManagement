function getDataMember() {
    let ary = [];
    $('.attrTable tr').each(function (a, b) {
        let name = $('.attrName', b).text();
        let value = $('.attrValue', b).find(":selected").text();
        ary.push({username: name, role: value});

    });
    return ary;
}

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });
    indexed_array["members"] = getDataMember();
    return indexed_array;
}


$(document).on("click", "#btn-add-member", function (e) {
    e.preventDefault();
    $("#error-message").addClass("d-none");
    let username = $("#member").val();
    let check = true;
    $.map(getDataMember(), function (n, i) {
        if (n.username === username) {
            check = false;
            return;
        }
    });
    if (!check) {
        return;
    }
    $.ajax({
        url: "/getMember/" + username,
        type: "GET",
        success: function (data) {
            let obj = JSON.parse(data);
            if (obj.success) {
                $("#member-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">'+obj.user.username+'</span> </td> <td class="pt-2"> <div class="rs-select2--border rs-select2--sm rs-select2--dark2 pl-2"> <select class="attrValue" name="type"> <option value="leader">Leader</option><option value="member">Member</option> </select> <div class="dropDownSelect2"></div> </div> </td><td class="pt-2"> <div class="table-data-feature pl-2"> <a href="" class="item" data-toggle="tooltip" data-placement="top" title="Delete"> <i class="fas fa-trash"></i> </a>\n' +
                    ' </div> </td> </tr>')
            } else {
                $("#error-message").removeClass("d-none");
                $("#error-message").text(obj.message);
            }
        },
        error: function (xhr) {
        },
    });
})

$(document).on("submit", "#register", function (e) {
    e.preventDefault();
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $("#error-container").empty();
    let $form = $("#register");
    let dataForm = getFormData($form);
    $.ajax({
        url: "/register",
        type: "POST",
        data: JSON.stringify(dataForm),
        contentType: "application/json",
        success: function (data) {
            let obj = JSON.parse(data);
            let str = '';
            if (obj.hasError) {
                $.map(obj.errors, function (n, i) {
                    str += n;
                    $("#error-container").append('<span class="text-danger">' + n + '</span><br>');
                });
                $("#error-container").removeClass("d-none");
                $(window).scrollTop(0);
            }

            //$("#form-content").html(data);
            $.LoadingOverlay("hide");
        },
        error: function (xhr) {
            console.log(xhr)
        },
    });
});
