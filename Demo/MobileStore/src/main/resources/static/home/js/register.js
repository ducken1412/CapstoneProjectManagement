/**
 * 
 */

$(document).ready(function() {

	jQuery.validator.addMethod("specialChars", function(value, element) {
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		var key = value;

		if (!regex.test(key)) {
			return false;
		}
		return true;
	}, "please use only alphanumeric or alphabetic characters");

	$("#register").validate({
		rules : {
			userName : {
				required : true,
				minlength : 5,
				specialChars : true,
				maxlength : 32,
			},
			email : {
				required : true,
				email : true,
			},
			encrytedPassword : {
				required : true,				
				minlength : 8,
				maxlength : 32,
			},
			repass : {
				required : true,
				minlength : 8,
				equalTo : "#encrytedPassword",
			}
		},
		messages : {
			userName : {
				required : "Please enter employee name",
				minlength : "Username at least 5 character",
				maxlength : "Username maximum 32 character",
			},
			email : {
				required : "Please enter email",
				email : "Invalid email",
			},
			encrytedPassword : {
				required : "Please enter password",
				minlength : "Password at least 8 character",
				maxlength : "Password maximum 32 character",
			},
			repass : {
				required : "Please enter password confirm",
				minlength : "Password at least 8 character",
				equalTo : "Password confirm not correct"
			}
		},
	});
});
