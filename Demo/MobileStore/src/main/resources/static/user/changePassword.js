jQuery(document).ready(function() {
	
	// change password
	$.validator.addMethod("checkSpaces", function(value, element) {
		return value.indexOf(" ") < 0 && value != "";
    }, "Input cannot contain spaces.");
	
	$.validator.addMethod("isSame", function(value, element) {
		return !(value == $("#oldPassword").val());
    }, "The new password must not be the same as the old password.");
	
	$("#form-change-password").validate({
        rules: {
        	oldPassword: {
                required: true,
                minlength: 8,
                maxlength: 30,
                checkSpaces: true
            },
            newPassword: {
		        required: true,
		        minlength: 8,
		        maxlength: 30,
		        checkSpaces: true,
		        isSame: true
		    },
		    confirmNewPassword: {
		        equalTo: newPassword
		    }
        },
        messages: {
        	oldPassword: {
                required: "Please enter password",
                minlength: "Password must contain at least 8 characters",
                maxlength: "Password must contain at less than 30 characters",
            },
            newPassword: {
                required: "Please enter password",
                minlength: "Password must contain at least 8 characters",
                maxlength: "New password must contain at less than 30 characters",
            },
            confirmNewPassword: {
            	equalTo: "The password confirmation does not match the new password",
            }
        },
        submitHandler: function () {
        	var confirmNewPassword = $("#confirmNewPassword").val();
        	var newPassword = $("#newPassword").val();
        	var oldPassword = $("#oldPassword").val();
        	if(newPassword != confirmNewPassword){
        		showError('Edit!', "Confirm password not equal password", 'OK')
        	}else{

            	var data = {};
            	data["newPassword"] = newPassword;
            	data["oldPassword"] = oldPassword;
            	data["confirmNewPassword"] = confirmNewPassword;
        		 $.ajax({
       			 	type: "post",
                    url: "/user/change-password",
                    contentType: "application/json", // Du lieu gui len Rest API co dang JSON
                    data: JSON.stringify(data), // object json -> string json
       				dataType: "json", // Du lieu Rest tra ve json
                    success: function (response) {
	                   	if(response.status == 'success'){
                            showMessageSuccess(response.message, 5000);
	                           
	                   	}else{
	                   		showError('Edit!', response.message, 'OK')
	                   	}
	                   	$("input[type=password]").val('');
                    }
                });
        	}
        }
    });
});