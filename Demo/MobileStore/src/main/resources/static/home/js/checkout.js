

$(document).ready(function() {
	$("#userInfor").change(function(){
		document.getElementById("checkout-form").reset();
	})
	
	$("#newInfor").change(function(){
		$("#customerName").val("");
		$("#email").val("");
		$("#address").val("");
		$("#phone").val("");
	})
});

$.validator.addMethod("validPhone", function (value, element) {
            return value.match(/0\d{7,15}/)
        }, "Phone number must start with 0 and length beetween 8-16 number");

$.validator.addMethod("notBank", function (value, element) {
    return value.trim().length>0;
}, "This field must not blank");

$.validator.addMethod("notSpecialChar", function (value, element) {
	return value.match(/^[a-zA-Z0-9 ]+$/)
}, "This field must not contain speacial char");



$(document).ready(function() {
    $('#checkout-form').validate({
        rules : {
        	customerName: {
                required : true,
                notBank : true,
                maxlength : 100,
                notSpecialChar: true,
            },
            email: {
            	email:true,
            },
            address: {
                required: true,
                minlength : 3,
                maxlength : 256,
                notBank : true,
            },
            phone: {
            	required : true,
            	validPhone:true,
            }
        }
    })
});

