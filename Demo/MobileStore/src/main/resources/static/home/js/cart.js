function getCart(){
	var cart =$.cookie("cart");
	try{
		cart =JSON.parse(cart);
	}catch{
		cart = null;
	}
	if(cart==null){
		cart = [];
	}
	return cart;
}
function countCartItem(){
	cart = getCart();
	var countCart=0;
	for(i=0;i<cart.length;i++){
		countCart+=cart[i].quantity;
	}
	return countCart;
}

function updateCountCartItem(quantity){
	var countCart= quantity + parseInt($("#countCart").text());
	$("#countCart").text(countCart);
}

$("#countCart").text(countCartItem());

function searchProduct(cart,productId){
	for(i=0;i<cart.length;i++){
		if(cart[i].id==productId){
			return i;
		}
	}
	return -1;
}


function processAdd(This,quantity){
	var productId=parseInt(This.attr("productId"));
	if(isNaN(productId)){
		return;
	}
	var cart =$.cookie("cart");
	var productName=This.attr("productName");
	var productQuantity=parseInt(This.attr("productQuantity"));
	
	if(quantity>productQuantity){
		showError("Error","Total <span style='color:red'>"+productQuantity+"</span> <b>"+productName+"</b> remain","Continue shopping");
		return ;
	}else {
		var cartSize = countCartItem();
		if(quantity+cartSize>20){
			showError("Error","You have <span style='color:red'>"+cartSize+" product(s)</span><b><br>Max product in cart is 20","Continue shopping");
			return ;
		}
	}
	
	if(cart!=undefined&&cart!='null'){
	
		
		var oldQuantity = 0;
		cart = getCart();
		var index = searchProduct(cart,productId)
		if(index>=0){
			oldQuantity=cart[index].quantity;
		}
		if((quantity+oldQuantity)>productQuantity){
			showError("Error","Total "+productQuantity+" <b>"+productName+"</b> remain you have already had "+oldQuantity,"Continue shopping");
			return ;
		}else if(quantity+oldQuantity>5){
			showError("Error","Total quantity > 5, you had "+oldQuantity+" <b>"+productName+"</b> in cart","Continue shopping");
			return ;
		}else{
			if(index>=0){
				cart[index].quantity=quantity+oldQuantity;
			}else{
				cart.push({"id":productId,"quantity":quantity})
			}
			
			$.cookie("cart", JSON.stringify(cart), {expires: 7, path:'/'});
		}
	}else{
		cart =[];
		cart.push({"id":productId,"quantity":quantity})
		$.cookie("cart", JSON.stringify(cart), {expires: 7, path:'/'});
		
	}
	updateCountCartItem(quantity)
	showSuccess("Success","<b>"+productName+"</b> x"+quantity+" added to cart","Countinue shopping");
}

$(".remove-product").click(function(e){
	e.preventDefault();
	This=$(this);
	showWarning("Delete","Delete this product","Delete",function(){
		var row = This.closest("tr");
		var productId=parseInt(This.attr("productId"));
		var cart = getCart();
		var index = searchProduct(cart,productId);
		var quantity =cart[index].quantity;
		if(index<0){
			window.location.reload();
		}else{
			cart.splice(index, 1);
			$.cookie("cart", JSON.stringify(cart), {expires: 7, path:'/'});
			if(cart.length>0){
				row.remove();
				updateCountCartItem(-quantity);
				generateTotalMoney();
			}else{
				window.location.reload();
			}
		}
	})
});

$(".delAllCart").click(function(){
	showWarning("Delete","Delete all product","Delete all",function(){
		$.cookie("cart","null", {expires: 7, path:'/'});
		window.location.reload();
	});
});




function formatNumber(num) {
	  return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
	}

function getMoneyValue(formattedMoney){
	return parseFloat(formattedMoney.replace(/[,]+/g, ""));
}

function generateTotalMoney(){
	var total = 0;
	var price = 0;
	var discount = 0;
	var quantity = 0;
	$(".cart-book-quantity").each(function(){
		price = parseFloat($(this).attr("price"));
		discount = parseFloat($(this).attr("discount"));
		quantity = parseInt($(this).val());
		total += price*quantity*discount;
	})
	total = (Math.round((total + Number.EPSILON) * 100) / 100).toFixed(2);
	$("#total").text("$ "+formatNumber(total));
}
generateTotalMoney();

$(".cart-book-quantity").change(function(){
	var quantity = parseInt($(this).val());
	var preQuantity = parseInt($(this).attr("preQuantity"));
	if(isNaN(quantity)){
		$(this).val(preQuantity);
		return;
	}
	
	
	var productQuantity = parseInt($(this).attr("productQuantity"));
	if(quantity<1||quantity>5){
		showError("Error","Product quantity from 1 to 5","Continue shopping");
		$(this).val(preQuantity);
		return;
	}else if((quantity+countCartItem()-preQuantity)>20){
		showError("Error","Total product >20, please remove other product(s)","Continue shopping");
		$(this).val(preQuantity);
		return;
	} else if((productQuantity-quantity)<0){
		if(productQuantity<0){
			productQuantity = 0;
		}
		showError("Error","Total "+productQuantity+" product(s) remain","Continue shopping");
		if(productQuantity>0){
			quantity = productQuantity;
		}else{
			quantity =1;
		}
		$(this).val(quantity);
	}
	
	//remove red alert if product available
	if(productQuantity>=quantity){
		$(this).closest('tr').removeClass('table-danger');	
	}
	
	$(this).attr("preQuantity",quantity)
	var price = parseFloat($(this).attr("price"));
	var discount = parseFloat($(this).attr("discount"));

	var totalAmount = price*quantity*discount;
	totalAmount = (Math.round((totalAmount + Number.EPSILON) * 100) / 100).toFixed(2);
	totalAmount = formatNumber(totalAmount)
	$(this).closest("tr").find(".total-amount").text(totalAmount);
	var productId=$(this).attr("productId");
	var cart = getCart();
	var index = searchProduct(cart,productId);
	
	cart[index].quantity = quantity;
	$.cookie("cart", JSON.stringify(cart), {expires: 7, path:'/'});
	$("#countCart").text(countCartItem());
	$("#countCart").text(countCartItem());
	generateTotalMoney();
})


