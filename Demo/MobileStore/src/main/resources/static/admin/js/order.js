
jQuery(document).ready(function ($) {
	var params = new URL(location.href).searchParams;
	var orderId = params.get('orderId');
	var fromDate = params.get('fromDate');
	var toDate = params.get('toDate');
	var status = params.get('status');
	$("#orderId").val(orderId);
	$("#fromDate").val(fromDate);
	$("#toDate").val(toDate);
	$("#status").val(status);
});