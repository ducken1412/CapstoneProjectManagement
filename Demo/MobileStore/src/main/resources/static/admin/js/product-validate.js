$(document).ready(function() {
    $('#product-form').validate({
        rules : {
            name: {
                required : true,
                maxlength : 100,
                minlength : 3
            },
            price: {
                required: true,
                min: 0
            },
            quantity: {
                required: true,
                min: 0
            },
            manufacturer: {
                required: true,
                maxlength : 100,
                minlength : 3
            },
            discount: {
            	min: 0,
            	max: 100
            },
            category: {
            	required: true,
            }
        }
    });
});
