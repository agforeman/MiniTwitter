function validateForm()
{
	var fullname = document.getElementById("fullname");
	var error_name = document.getElementById("fullname_error");
	var password = document.getElementById("password");
	var confirmpassword = document.getElementById("confirmPassword");
	var error_password = document.getElementById("confirmPassword_error");

	if(password.value !== confirmpassword.value)
	{
		confirmpassword.style.backgroundColor = "yellow";
		error_password.style.display = "inline";
		return false;
	}
	else
	{
		confirmpassword.style.backgroundColor = "white";
		error_password.style.display = "none";
	}

	//check for spaces in full name
	if (/\s/.test(fullname.value))
	{
		fullname.style.backgroundColor = "white";
		error_name.style.display = "none";
	}
	else
	{	
		fullname.style.backgroundColor = "yellow";
		error_name.style.display = "inline";
		return false;
	}
	return true;
}