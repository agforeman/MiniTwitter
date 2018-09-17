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
        error_message.innerHTML += "<br />";
        error_message.className = "isVisible";
    }   
    return valid;
}

// Cleanup all the formatting changes caused by errors from the previous submit
// attempt. IE changes all forms background to white and hides error messages.
function cleanup() {
    // Clear the innerHTML for error_message and hide the error div.
    document.getElementById("error_message").innerHTML = "";
    
    // Clear error formatting that might be left over from a bad submit
    var listofelements = document.getElementsByClassName("isVisible");
    
    // Set all error "*" to notVisible. i is not incremented because as the 
    // elements' classes are changed the are automatically removed from the 
    // listofelements.
    for(var i = 0; i < listofelements.length; ) {
        // Ignore the security answer when hiding all the error spans
        if(listofelements[i].classList.contains("noCleanup")) {
            // If we skip an element we must increment i to move to the next
            // element. This also prevents us from getting stuck in the loop.
            ++i;
            continue;
        }
        listofelements[i].className = "notVisible";
    }
    listofelements = document.getElementsByTagName("input");
    for(var i = 0; i < listofelements.length; ++i) {
        // ignore the buttons when clearing <input> formatting
        if(listofelements[i].classList.contains("button")) {
            continue;
        }
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
        error_password.className = "isVisible";
        error_confirm.className = "isVisible";                
        error_message.innerHTML += "Error! Passwords do not match!<br />";
        return false;
    }
    return true;
}

function checkNameLength(error_message) {
    var fullname = document.getElementById("fullname");
    var name = fullname.value;
    var error_name = document.getElementById("fullname_error");
    //check for spaces in full name
	if (!(/\s/.test(fullname.value)) || name.charAt(name.length-1)=== " ")
        {
            fullname.style.backgroundColor = "yellow";
            error_name.className = "isVisible";                
            error_message.innerHTML += "Full Name is not valid!<br />";
            return false;
	}
    return true;
}

function checkForInjection(error_message) {	
    //check if any input contains a single quote
    var quote = /'/;
    var valid = true;
    
    // Get a result set of all elements that have the class input
    var listofelements = document.getElementsByTagName("input");
    for(var i = 0; i < listofelements.length; ++i) {
        // Test each element for a single quote.
        if (quote.test(listofelements[i].value)) {
            switch(listofelements[i].id) {
                case "confirm_password" :
                    document.getElementById("confirm_error").className = "isVisible";
                    break;
                case "dateofbirth" :
                    document.getElementById("dateofbirth_error").className = "isVisible";
                    break;
                case "email" :
                    document.getElementById("email_error").className = "isVisible";
                    break;
                case "fullname" :
                    document.getElementById("fullname_error").className = "isVisible";
                    break;
                case "password" :
                    document.getElementById("password_error").className = "isVisible";
                    break;
                case "username" :
                    document.getElementById("username_error").className = "isVisible";
                    break;
                case "security_answer":
                    document.getElementById("security_answer_error").className = "isVisible";
                default:
                    break;
            }
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
//Displays security answer box.
function DynamicForm() {
    
    var answer = document.getElementById("security_answer");  
    answer.className = "isVisible noCleanup";
}
