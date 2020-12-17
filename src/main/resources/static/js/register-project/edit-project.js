
$(document).ready(function () {
    $('#nameViHidden').val($("#nameVi").val());
    $('#nameHidden').val($("#name").val());
    $( '#nameViHidden' ).change(function() {
        $('#nameVi').val($("#nameViHidden").val());
    });
    $( '#nameHidden').change(function() {
        $('#name').val($("#nameHidden").val());
    });
    $.ajax({
        url: "/getMemberEdit",
        type: "GET",
        success: function (data) {
            debugger;
            let objs = JSON.parse(data);
            for (let obj in objs){
                var a = objs[obj];
                for(let pro in a){
                    var b = a[pro].username;
                    if (b != undefined) {
                        if(a[pro].status === 'registed_capstone'){
                            if(a[pro].role ==='student_leader'){
                                $("#member-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">' + a[pro].username + '</span> </td> <td class="pt-2"> <div class="rs-select2--border rs-select2--sm rs-select2--dark2 pl-2"> ' +
                                    '<select class="btn-sm btn-secondary dropdown-toggle attrValue"  name="type"> ' +
                                    '<option value="leader"  selected>Leader</option>' +
                                    '<option value="member">Member</option> ' +
                                    '</select> <div class="dropDownSelect2">' +
                                    '</div> </div> </td><td class="pt-2">' +
                                    ' <div class="table-data-feature pl-2"> </a>\n' +
                                    ' </div> </td> </tr>')
                            }else {
                                $("#member-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">' + a[pro].username + '</span> </td> <td class="pt-2"> <div class="rs-select2--border rs-select2--sm rs-select2--dark2 pl-2"> ' +
                                    '<select class="btn-sm btn-secondary dropdown-toggle attrValue"  name="type"> ' +
                                    '<option value="leader"  >Leader</option>' +
                                    '<option value="member" selected >Member</option> ' +
                                    '</select> <div class="dropDownSelect2">' +
                                    '</div> </div> </td><td class="pt-2">' +
                                    ' <div class="table-data-feature pl-2"> </a>\n' +
                                    ' </div> </td> </tr>')
                            }

                        }else {
                            if(a[pro].role ==='student_leader'){
                                $("#member-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">' + a[pro].username + '</span> </td> <td class="pt-2"> <div class="rs-select2--border rs-select2--sm rs-select2--dark2 pl-2"> ' +
                                    '<select class="btn-sm btn-secondary dropdown-toggle attrValue"  name="type"> ' +
                                    '<option value="leader"  selected>Leader</option>' +
                                    '<option value="member">Member</option> ' +
                                    '</select> <div class="dropDownSelect2">' +
                                    '</div> </div> </td><td class="pt-2">' +
                                    ' <div class="table-data-feature pl-2"> </a> <a href="" class="item del-member" data-toggle="tooltip" data-placement="top" title="Delete"> <i class="fas fa-trash fa-xs"></i> </a>\n' +
                                    ' </div> </td> </tr>')
                            }else {
                                $("#member-table").append('<tr class="tr-shadow"> <td class="pt-2"> <span class="block-email attrName">' + a[pro].username + '</span> </td> <td class="pt-2"> <div class="rs-select2--border rs-select2--sm rs-select2--dark2 pl-2"> ' +
                                    '<select class="btn-sm btn-secondary dropdown-toggle attrValue"  name="type"> ' +
                                    '<option value="leader"  >Leader</option>' +
                                    '<option value="member" selected >Member</option> ' +
                                    '</select> <div class="dropDownSelect2">' +
                                    '</div> </div> </td><td class="pt-2">' +
                                    ' <div class="table-data-feature pl-2"> </a> <a href="" class="item del-member" data-toggle="tooltip" data-placement="top" title="Delete"> <i class="fas fa-trash fa-xs"></i> </a>\n' +
                                    ' </div> </td> </tr>')
                            }
                        }
                    }

                }

            }

        },
        error: function (xhr) {
        },
    });
});

function getDataMember() {
    let ary = [];
    $('.attrTable tr').each(function (a, b) {
        let name = $('.attrName', b).text();
        let value = $('.attrValue', b).find(":selected").text();
        if (name !== '' && value !== '') {
            ary.push({username: name, role: value});
        }
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
        if (n.username.toUpperCase() === username.toUpperCase()) {
            check = false;
            return;
        }
    });
    if (!check) {
        return;
    }
    $.ajax({
        url: "/getMemberEdit/" + username,
        type: "GET",
        success: function (data) {
            let obj = JSON.parse(data);
            if (obj.success) {
                $("#member-table").append('<tr class="tr-shadow">' +
                    ' <td class="pt-2"> <span class="block-email attrName">' + obj.user.username + '</span> </td> ' +
                    '<td class="pt-2"> <div class="rs-select2--border rs-select2--sm rs-select2--dark2 pl-2"> ' +
                    '<select class="btn-sm btn-secondary dropdown-toggle attrValue" name="type"> ' +
                        '<option value="member">Member</option> ' +
                        '<option value="leader">Leader</option> ' +
                    '</select> <div class="dropDownSelect2"></div> </div> </td><td class="pt-2"> <div class="table-data-feature pl-2"> <a href="" class="item del-member" data-toggle="tooltip" data-placement="top" title="Delete"> <i class="fas fa-trash fa-xs"></i> </a>\n' +
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
$(document).on("click", ".del-member", function (e) {
    e.preventDefault();
    if (confirm('Do you want to delete this?')) {
        $(this).closest('tr').remove();
    }
})

$(document).on("submit", "#update", function (e) {
    e.preventDefault();
    $("#loading-add").attr("hidden", false);
    $("#btn-update-project").attr("disabled", true);
    $.LoadingOverlay("show", {
        size: 50,
        maxSize: 50,
    });
    $("#error-container").empty();
    let $form = $("#update");
    let dataForm = getFormData($form);
    $.ajax({
        url: "/updateProject",
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
                $("#loading-add").attr("hidden", true);
                $("#btn-update-project").attr("disabled", false);
            } else {
                $.showNotification({
                    body: obj.message,
                    type: "success",
                    duration: 3000,
                    shadow: "0 2px 6px rgba(0,0,0,0.2)",
                    zIndex: 100,
                    margin: "1rem"
                });
                setTimeout(
                    function () {
                        window.location.href = "/lecturers";
                    }, 2000);
                $("#loading-add").attr("hidden", true);
            }
            //$("#form-content").html(data);

            $.LoadingOverlay("hide");
        },
        error: function (xhr) {
            console.log(xhr)
        },
    });
});

