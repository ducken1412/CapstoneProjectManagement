$(document).ready(function () {
    $("#registerForm").validate({
        rules: {
        	userName : {
        		required: true,
        		minlength: 3,
        		maxlength: 30
        	},
            email: {
                required: true,
                email: true,
                minlength: 5,
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 30
            },
            confirmPassword : {
            	required: true,
            	equalTo: "#pwd"
            }
        },
        messages: {
        	userName : {
        		required: "Please enter username",
                email : "Please enter correct email",
                minlength: "Email must contain at least 3 characters",
                maxlength: "Email must contain at less than 30 characters",
        	},
            email: {
                required: "Please enter email",
                email : "Please enter correct email",
                minlength: "Email must contain at least 5 characters",
            },
            password: {
                required: "Please enter password",
                minlength: "Password must contain at least 8 characters",
                maxlength: "Password must contain at less than 30 characters",
            },
            confirmPassword: {
                required: "Please enter password",
                equalTo: "repassword not equal password"
            }
        },
//        submitHandler: function () {
//        	var email = $("#registerForm input[name='email']").val();
//        	var username = $("#registerForm input[name='userName']").val();
//        	var password = $("#registerForm input[name='password']").val();
//        	var repassword = $("#registerForm input[name='confirmPassword']").val();
//        	
//            $.ajax({
//                type: "post",
//                url: "/HieuNM30_JWEB.P.A101/register-user",
//                data : {
//                	email : email,
//                	username : username,
//                	password : password,
//                	repassword : repassword
//    			},
//                success: function (response) {
//                    if(response.status == 'success'){
//                    	window.location.href = "/HieuNM30_JWEB.P.A101/login";
//                    }
//                    else{
//                		$("#response-message").text(response.message);
//                    }
//                }
//            });
//        }
    });
});