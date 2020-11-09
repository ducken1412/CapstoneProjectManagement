//
//(function ($) {
//    "use strict";
//
//    /*==================================================================
//    [ Focus Contact2 ]*/
//    $('.input100').each(function(){
//        $(this).on('blur', function(){
//            if($(this).val().trim() != "") {
//                $(this).addClass('has-val');
//            }
//            else {
//                $(this).removeClass('has-val');
//            }
//        })    
//    })
//
//    /*==================================================================
//    [ Validate ]*/
//    var input = $('.validate-input .input100');
//
//    $('.validate-form').on('submit',function(){
//        var check = true;
//
//        for(var i=0; i<input.length; i++) {
//            if(validate(input[i]) == false){
//                showValidate(input[i]);
//                check=false;
//            }
//        }
//
//        return check;
//    });
//
//
//    $('.validate-form .input100').each(function(){
//        $(this).focus(function(){
//           hideValidate(this);
//        });
//    });
//
//    function validate (input) {
//        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
//            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
//                return false;
//            }
//        }
//        else {
//            if($(input).val().trim() == '' || $(input).val().includes(' ')){
//                return false;
//            }
//        }
//    }
//
//    function showValidate(input) {
//        var thisAlert = $(input).parent();
//
//        $(thisAlert).addClass('alert-validate');
//    }
//
//    function hideValidate(input) {
//        var thisAlert = $(input).parent();
//
//        $(thisAlert).removeClass('alert-validate');
//    }
//    
//
//})(jQuery);


$(document).ready(function () {
	$.validator.addMethod("checkSpaces", function(value, element) {
		return value.indexOf(" ") < 0 && value != "";
    }, "Input cannot contain spaces.");

    $("#login-form").validate({
        rules: {
        	username: {
                required: true,
                minlength: 5,
                maxlength: 50,
                checkSpaces: true
            },
            password: {
                required: true,
                minlength: 8,
                maxlength: 30,
                checkSpaces: true
            }
        },
        messages: {
        	username: {
                required: "Please enter user name",
                minlength: "Username must contain at least 5 characters",
                maxlength: "Username must contain at less than 50 characters",
            },
            password: {
                required: "Please enter password",
                minlength: "Password must contain at least 8 characters",
                maxlength: "Password must contain at less than 30 characters",
            }
        },
    });

});
