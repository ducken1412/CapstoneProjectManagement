function showSuccess(title, content, button) {
	Swal.fire({
		icon : 'success',
		title : title,
		html: content,
		showConfirmButton : true,
		confirmButtonText : button,
		timer : 2000,
	});
}

function showError(title,content,button){
	Swal.fire({
		icon : 'error',
		title : title,
		html: content,
		showConfirmButton : true,
		confirmButtonText : button,
//		timer : 3500,
	});
}

function showWarning(title,content,button,callback){
	Swal.fire({
		  title: title,
		  text: content,
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: button,
		}).then((result) => {
		  if (result.value) {
			  callback()
		  }
		})
}


$(document).on('click','.product_cart_button',function(e){
	e.preventDefault();
	var This = $(this);
	var productId=parseInt(This.attr("productId"));
	if(isNaN(productId)){
		return;
	}
	var productName=This.attr("productName");
	var quantity = 1;
	
	if(quantity<1||quantity>10){
		showError("Error","Quantity is invalid","Continue shopping");
		return;
	}
	
	var title ='<b>Add '+productName+'</b> x '+quantity+' to cart'
	Swal.fire({
	  html: title,
	  icon: 'question',
	  showCancelButton: true,
	  confirmButtonColor: '#3085d6',
	  cancelButtonColor: '#d33',
	  confirmButtonText: 'Add to cart!',
	  cancelButtonText: 'Cancel',
	}).then((result) => {
		if(result.value){
			processAdd(This,quantity)
		}
	
	})
});
