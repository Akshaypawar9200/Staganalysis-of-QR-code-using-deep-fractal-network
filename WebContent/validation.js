function isPasswordMatch(newpassword1, newpassword2)
{
	if(newpassword1.value==newpassword2.value){
		return true;
	}else{
		alert("Password Mismatch... Please try again!");
		newpassword2.focus();
		return false;
	}
}
