
$('.shop_sorting_button').on('click', function() {
	var data = $(this).text();
	$('.sorting_text').text(data);
	if (data == "Price Descending") {
		$('#sorting').val(1);
	} else if (data == "Price Increasing") {
		$('#sorting').val(2);
	} else {
		$('#sorting').val(0);
	}
	getAllData();
});


