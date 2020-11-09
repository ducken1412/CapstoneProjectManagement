function getCategory() {
	return $(".category.active").attr("categoryId");
}

function getPriceMin() {
	var priceRange = $('#amount').val();
	var priceMin = parseFloat(priceRange.split('-')[0].replace('$', ''));
	return priceMin;
}

function getPriceMax() {
	var priceRange = $('#amount').val();
	var priceMax = parseFloat(priceRange.split('-')[1].replace('$', ''));
	return priceMax;
}
function getCondition() {
	return $('#condition').val();
}

function getSortedBy() {
	return $('#sorting').val();
}

function getName() {
	return $('#keyWord').val();
}

function getAllData() {
	$.LoadingOverlay("show", {
		 background : "rgba(255, 255, 255, 0.9)",
		 size : 5
	});
	categoryId = getCategory();
	priceMax = getPriceMax();
	priceMin = getPriceMin();
	sortedBy = getSortedBy();
	condition = getCondition();
	name = getName();
	var data = {
		categoryId : categoryId,
		priceMax : priceMax,
		priceMin : priceMin,
		sortedBy : sortedBy,
		condition : condition,
		name : name
	}
	$.ajax({
		url : "/list-product",
		type : 'GET',
		data : data,
		success : function(result) {
			$('#shop_content').html(result);
			window.history.pushState('', '', '/shop' + rewriteUrl(data));
		},
		error: function(xhr){
			if(xhr.status == 302 || xhr.status == 200){
				window.location.href = '/shop';
			}
		},
		complete: function() {
			setTimeout(() => {
				$.LoadingOverlay("hide");
			}, 600);
		}
	});
}

$(document).on('click', '#removeKeyword', function(e) {
	e.preventDefault();
	$('#keyWord').val('');
	getAllData();
});

$(document).on('click', '.page-link', function(e) {
	$.LoadingOverlay("show", {
		 background : "rgba(255, 255, 255, 0.9)",
		 size : 5
	});
	e.preventDefault();
	var str = $(this).attr("href");
	$.ajax({
		url : "/list-product" + str,
		type : 'GET',
		success : function(result) {
			$('#shop_content').html(result);
			window.scrollTo(0, 500);
			window.history.pushState('', '', '/shop' + str);
		},
		error: function(xhr){
			if(xhr.status == 302 || xhr.status == 200){
				window.location.href = '/shop';
			}
		},
		complete: function() {
			setTimeout(() => {
				$.LoadingOverlay("hide");
			}, 600);
		}
	});
})

function rewriteUrl(search) {
	var url = "";
	if (search.categoryId > 0) {
		if("" == url) {
			url+="?";
		}else {
			url+="&";
		}
		url += "category=" + search.categoryId;
	}
	
	if (search.priceMax >= 0) {
		if("" == url) {
			url+="?";
		}else {
			url+="&";
		}
		url += "priceMax=" + search.priceMax;
	}
	if (search.priceMin >= 0) {
		if("" == url) {
			url+="?";
		}else {
			url+="&";
		}
		url += "priceMin=" + search.priceMin;
	}
	if (search.condition > 0) {
		if("" == url) {
			url+="?";
		}else {
			url+="&";
		}
		url += "condition=" + search.condition;
	}
	if (search.sortedBy > 0) {
		if("" == url) {
			url+="?";
		}else {
			url+="&";
		}
		url += "sortedBy=" + search.sortedBy;
	}
	
	if (search.name != null && search.name != "") {
		if("" == url) {
			url+="?";
		}else {
			url+="&";
		}
		url += "keyword=" + search.name;
	}

	return url;
}

function initCategory() {
	const params = new URL(location.href).searchParams;
	const category = params.get('category');
	if(category != null) {
		$('.category').each(function(){
			if($(this).attr('categoryId') == Number(category)) {
				$(".category.active").removeClass("active");
				$(this).addClass("active");
			}
		});
	}
}

function initCondition() {
	const params = new URL(location.href).searchParams;
	const condition = params.get('condition');
	
	if(condition != null) {
		if (Number(condition) == 1) {
			$('.condition_text').text('New');
			$('#condition').val(1);
		} else if (Number(condition)  == 2) {
			$('#condition').val(2);
			$('.condition_text').text('Old');
		} else if (Number(condition)  == 3) {
			$('#condition').val(3);
			$('.condition_text').text('Refurbished');
		} else {
			$('#condition').val(0);
			$('.condition_text').text('All Condition');
		}
		
	}
}

function initPriceSlider()
{
	const params = new URL(location.href).searchParams;
	const priceMin = params.get('priceMin');
	const priceMax = params.get('priceMax');
	
	var minValue = 0;
	var maxValue = 3000;
	
	if(priceMin != null) {
		minValue = Number(priceMin);
	}
	
	if(priceMax != null) {
		maxValue = Number(priceMax);
	}
	
	if($("#slider-range").length)
	{
		$("#slider-range").slider(
		{
			range: true,
			min: 0,
			max: 3000,
			values: [ minValue, maxValue ],
			slide: function( event, ui )
			{
				$( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
			}
		});
		$( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) + " - $" + $( "#slider-range" ).slider( "values", 1 ) );
	}
}


$('#slider-range').on('mouseup', function() {
	getAllData();
});


$(document).on('click', '.category', function(e) {
	e.preventDefault();
	$(".category.active").removeClass("active");
	$(this).addClass("active");
	getAllData();
})

$('.shop_condition_button').on('click', function() {
	var data = $(this).text();
	$('.condition_text').text(data);
	if (data == "New") {
		$('#condition').val(1);
	} else if (data == "Old") {

		$('#condition').val(2);
	} else if (data == "Refurbished") {
		$('#condition').val(3);
	} else {
		$('#condition').val(0);
	}
	getAllData();
});

