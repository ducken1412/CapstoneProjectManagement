function showSuccess(title, content, button) {
	Swal.fire({
		icon : 'success',
		title : title,
		html: content,
		showConfirmButton : true,
		confirmButtonText : button,
		timer : 20000,
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

function showMessageSuccess(title, time){
	const Toast = Swal.mixin({
		toast: true,
		position: 'top',
		showConfirmButton: false,
		timer: time,
		timerProgressBar: true,
		onOpen: (toast) => {
			toast.addEventListener('mouseenter', Swal.stopTimer)
			toast.addEventListener('mouseleave', Swal.resumeTimer)
		}
	})
	Toast.fire({
		icon: 'success',
		title: title
	})
}

function showMessageError(title, time){
	const Toast = Swal.mixin({
		toast: true,
		position: 'top',
		showConfirmButton: false,
		timer: time,
		timerProgressBar: true,
		onOpen: (toast) => {
			toast.addEventListener('mouseenter', Swal.stopTimer)
			toast.addEventListener('mouseleave', Swal.resumeTimer)
		}
	})
	Toast.fire({
		icon: 'error',
		title: title
	})
}

