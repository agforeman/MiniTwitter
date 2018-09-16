function validateForm()
{
    var error_message = document.getElementById("error_message"); 
    var valid = true;

    cleanup();
        
    valid = checkPasswordMatch(error_message);
    valid = checkNameLength(error_message) && valid;
    valid = checkForInjection(error_message) && valid;
    valid = checkPasswordRequirement(error_message) && valid;
          
    // If there were any errors display them.
    if(!valid){
        error_message.style.display = "inline";
    }   
    return valid;
}

// Cleanup all the formatting changes caused by errors from the previous submit
// attempt. IE changes all forms background to white and hides error messages.
function cleanup() {
    // Clear the innerHTML for error_message and hide the error div.
    document.getElementById("error_message").innerHTML = "";
    
    // Clear error formatting that might be left over from a bad submit
    var listofelements = document.getElementsByClassName("notVisible");
    for(var i = 0; i < listofelements.length; ++i) {
        listofelements[i].style.display = "none";
    }
    listofelements = document.getElementsByClassName("input");
    for(var i = 0; i < listofelements.length; ++i) {
        listofelements[i].style.backgroundColor = "white";
    }
}

function checkPasswordMatch(error_message) {
    var password = document.getElementById("password");
    var confirmpassword = document.getElementById("confirm_password");
    var error_password = document.getElementById("password_error");
    var error_confirm = document.getElementById("confirm_error");
    
    if(password.value !== confirmpassword.value)
    {
        password.style.backgroundColor = "yellow";
        confirmpassword.style.backgroundColor = "yellow";
        error_password.style.display = "inline";
        error_confirm.style.display = "inline";                
        error_message.innerHTML += "Error! Passwords do not match!<br />";
        return false;
    }
    return true;
}

function checkNameLength(error_message) {
    var fullname = document.getElementById("fullname");
    var error_name = document.getElementById("fullname_error");
    //check for spaces in full name
	if (!(/\s/.test(fullname.value)))
        {
            fullname.style.backgroundColor = "yellow";
            error_name.style.display = "inline";                
            error_message.innerHTML += "Full Name is not valid!<br />";
            return false;
	}
    return true;
}

function checkForInjection(error_message) {	
    var error_name = document.getElementById("fullname_error");
    var error_username = document.getElementById("username_error");
    var error_email = document.getElementById("email_error");
    var error_password = document.getElementById("password_error");
    var error_confirm = document.getElementById("confirm_error");
    var error_dateofbirth = document.getElementById("dateofbirth_error");
    var error_security_question = document.getElementById("security_question_error");
    var error_security_answer = document.getElementById("security_answer_error");
    
    
    //check if any input contains a single quote
    var quote = /'/;
    var valid = true;
    
    // Get a result set of all elements that have the class input
    var listofelements = document.getElementsByClassName("input");
    for(var i = 0; i < listofelements.length; ++i) {
        // Test each element for a single quote.
        if (quote.test(listofelements[i].value)) {
            valid = false;
            listofelements[i].style.backgroundColor = "yellow";
        }
    }
    if(!valid) {
        error_message.innerHTML += "Input has invalid characters!<br />";
    }
    return valid;
}
    
function checkPasswordRequirement(error_message) {
    
    var password = document.getElementById("password");
    var error_password = document.getElementById("password_error");
    var lowerCaseLetters = /[a-z]/g;
    var upperCaseLetters = /[A-Z]/g;
    var numbers = /[0-9]/g;
    var valid = true;
    
    //check for lower case letters
    if (!(password.value.match(lowerCaseLetters))) {         
        error_message.innerHTML += "Password must contain a lower case letter!<br />";
        valid = false;
    }
    //check for upper case letters
    if (!(password.value.match(upperCaseLetters))) {               
        error_message.innerHTML += "Password must contain an upper case letter!<br />";
        valid = false;
    }
    //check for a number
    if (!(password.value.match(numbers))) {                
        error_message.innerHTML += "Password must contain a number!<br />";
        valid = false;
    }
    //valid is false
    if(!valid) {
        password.style.backgroundColor = "yellow";
        error_password.className = "isVisible";
        return false;
    }
    return true;
}

function DynamicForm() {
    
    var answer = document.getElementById("security_answer");  
    answer.className = "isVisible";
}
